package database;

import java.util.*;

import ast.Exp;
import astvisitor.AttrConstraintEvaluator;
import astvisitor.ExpEvaluator;
import exception.DatabaseException;

public class Table {
	
	private String name;
	private Schema schema;
	private List<Tuple> tuples;
	
	public Table(String name, Schema schema) {
		this.name = name;
		this.schema = schema;
		tuples = new ArrayList<Tuple>();
		iteratorIndex = 0;
	}

	
	private void verifyPrimaryKeyUniqueness(Tuple tuple, List<Tuple> existingTuples)
			throws DatabaseException {
		
		for (Tuple t : existingTuples) {
			boolean keyIsDifferent = false;
			for (Integer position : schema.getPrimaryKeyPositions()) {
				Object value = t.getValueAt(position);
				Object newValue = tuple.getValueAt(position);
				if (!Tuple.valuesEqual(value, newValue)) {
					keyIsDifferent = true;
					break;
				}
			}
			if (!keyIsDifferent) {
				throw new DatabaseException("Primary key uniqueness constraint not met.");
			}
		}
	}
	
	
	
	private void verifyForeignKeyConstraints(Tuple tuple) throws DatabaseException {
		
		for (Schema.ForeignKey fk : schema.getForeignKeys()) {
			
			Table refTable = fk.getRefTable();
			
			// get the values of the foreign key attributes of newtuple
			int[] localKeyPositions = fk.getForeignKeyPositions();
			Object[] localKeyValues = new Object[localKeyPositions.length];
			for (int i=0; i<localKeyValues.length; ++i) {
				localKeyValues[i] = tuple.getValueAt(
						localKeyPositions[i]);
			}
			
			// iterate through tuples of refTable looking for one with
			// matching primary key
			int[] refKeyPositions = refTable.getSchema().getPrimaryKeyPositions();
			boolean matchFound = false;
			for (Tuple t : refTable.getTuples()) {
				matchFound = true;
				for (int i=0; i<refKeyPositions.length; ++i) {
					if (!Tuple.valuesEqual(localKeyValues[i],
							t.getValueAt(refKeyPositions[i]))) {
						matchFound = false;
						break;
					}
				}
				if (matchFound)
					break;
			}
			if (!matchFound) {
				throw new DatabaseException("Referential constraint to table '"
						+refTable.getName()+"' not met.");
			}
		}
	}
	
	
	
	public void addTuple(Tuple newTuple) throws DatabaseException {
		
		Attribute[] attributes = schema.getAttributes();
		Object[] values = newTuple.getValues();
		
		if (values.length != attributes.length) {
			throw new DatabaseException("Tuple does not have the correct number of values"+
					" as specified by the schema of table '"+name+"'.");
		}
		
		// for each value, check if it complies with its attribute's type and constraint
		for (int i=0; i<attributes.length; ++i) {
			AttrConstraintEvaluator.verifyValueComplies(
					newTuple.getValueAt(i), schema.getAttribute(i));
		}
		
		// check primary key uniqueness constraint
		verifyPrimaryKeyUniqueness(newTuple, tuples);
				
		// check foreign key referential integrity constraint
		// NOTE: new tuple cannot reference itself
		verifyForeignKeyConstraints(newTuple);
		
		tuples.add(newTuple);
	}
	
	
	
	
	public void deleteTuples(Exp condition) throws DatabaseException {
		
		if (condition == null) {
			tuples.clear();
			return;
		}
		
		// start a new tuples list
		 List<Tuple> nextTuples = new ArrayList<Tuple>();
		
		// add to it the tuples from the old list that fail the condition
		Tuple[] refTuples = new Tuple[1];
		Schema[] parentSchemas = new Schema[1];
		parentSchemas[0] = schema;
		for (Tuple t : tuples) {
			refTuples[0] = t;
			if (!ExpEvaluator.evaluateCondition(
					condition, refTuples, parentSchemas)) {
				nextTuples.add(t);
			}
			/* else {
			 *	// propagate delete, not necessary for this project
			}*/
		}
		tuples = nextTuples;
	}
	
	
	public void updateTuples(Exp condition, String[] updateAttrNames,
				Object[] updateValues) throws DatabaseException {
		
		// start a new tuples list
		List<Tuple> nextTuples = new ArrayList<Tuple>();
		
		// find each attribute that's being updated
		Attribute[] updateAttributes = new Attribute[updateAttrNames.length];
		for (int i=0; i<updateAttributes.length; ++i) {
			updateAttributes[i] = schema.getAttribute(updateAttrNames[i]);
			if (updateAttributes[i] == null) {
				throw new DatabaseException("Attribute '"+updateAttrNames[i]+
						"' does not exist.");
			}
		}
		
		// make sure each update value complies with its attribute
		for (int i=0; i<updateAttributes.length; ++i) {
			AttrConstraintEvaluator.verifyValueComplies(
					updateValues[i], updateAttributes[i]);
		}
		
		// go through old tuples and find those that pass condition
		// add them to new tuple, then run a primary key uniqueness check
		Tuple[] refTuples = new Tuple[1];
		Schema[] parentSchemas = new Schema[1];
		parentSchemas[0] = schema;
		for (Tuple t : tuples) {
			// if this tuple will be updated, make a copy of it and update that.
			// then do a primary key uniqueness check and add it to nextTuples
			refTuples[0] = t;
			if (ExpEvaluator.evaluateCondition(
					condition, refTuples, parentSchemas)) {
				Tuple tCopy = new Tuple(t);
				for (int i=0; i<updateAttributes.length; ++i) {
					tCopy.setValueAt(updateAttributes[i].getPosition(),
							updateValues[i]);
				}
				verifyPrimaryKeyUniqueness(tCopy, nextTuples);
				nextTuples.add(tCopy);
			}
			// otherwise, do a primary key uniqueness check on the original tuple
			// and add it to nextTuples
			else {
				verifyPrimaryKeyUniqueness(t, nextTuples);
				nextTuples.add(t);
			}
		}
		
		// move to new table state.  do this before checking referential integrity
		// since some may reference tuples in this table.
		// NOTE: it's possible a tuple will reference itself
		List<Tuple> prevTuples = tuples;
		tuples = nextTuples;
		
		// check referential constraints of updated table.  If not met,
		// revert to the old table.
		try {
			for (Tuple t : tuples)
				verifyForeignKeyConstraints(t);
		} catch (DatabaseException e) {
			tuples = prevTuples;
			throw e;
		}
	}
	
	
	
	public void print() {
		schema.print();
		for (Tuple t : tuples) {
			t.print(schema);
		}
	}
	
	
	public String getName() {
		return name;
	}
	
	public Schema getSchema() {
		return schema;
	}
	
	public List<Tuple> getTuples() {
		return tuples;
	}

	
	// iterator stuff
	
	private int iteratorIndex;
	
	public void resetIterator() {
		iteratorIndex = 0;
	}
	
	public boolean hasNext() {
		return (iteratorIndex < tuples.size());
	}
	
	public Tuple getNextTuple() {
		Tuple ret = tuples.get(iteratorIndex++);
		return ret;
	}
}

