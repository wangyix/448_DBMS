package database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	

	private void verifySchemaCompliance(Tuple tuple) throws DatabaseException {
		Attribute[] attributes = schema.getAttributes();
		Object[] values = tuple.getValues();
		if (values.length != attributes.length) {
			throw new DatabaseException("Tuple does not have the correct number of values.");
		}
		for (int i=0; i<attributes.length; ++i) {
			Object castedValue = AttrConstraintEvaluator.verifyValueComplies(
					tuple.getValueAt(i), attributes[i]);
			tuple.setValueAt(i, castedValue);
		}
	}
	private void verifyPrimaryKeyUniqueness(Tuple newTuple, List<Tuple> existingTuples)
			throws DatabaseException {
		
		// get types and primary key attributes of this tuple
		int[] primaryKeyPositions = schema.getPrimaryKeyPositions();
		Object[] newTupleKeyValues = new Object[primaryKeyPositions.length];
		Attribute.Type[] newTupleKeyTypes = new Attribute.Type[primaryKeyPositions.length];
		for (int i=0; i<primaryKeyPositions.length; ++i){
			newTupleKeyValues[i] = newTuple.getValueAt(primaryKeyPositions[i]);
			newTupleKeyTypes[i] = schema.getAttributes()[primaryKeyPositions[i]].getType();
		}
		
		// go through all existingTuples and compare key attribute values
		for (Tuple existingTuple : existingTuples) {
			boolean keyIsDifferent = false;
			for (int i=0; i<primaryKeyPositions.length; ++i) {
				Object tKeyValue = existingTuple.getValueAt(primaryKeyPositions[i]);
				if (!Tuple.valuesEqual(newTupleKeyTypes[i], newTupleKeyValues[i], tKeyValue)) {
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
			Attribute.Type[] localKeyTypes =  new Attribute.Type[localKeyPositions.length];
			for (int i=0; i<localKeyPositions.length; ++i) {
				localKeyValues[i] = tuple.getValueAt(localKeyPositions[i]);
				localKeyTypes[i] = schema.getAttributes()[localKeyPositions[i]].getType();
			}
			
			// iterate through tuples of refTable looking for one with
			// matching primary key
			int[] refKeyPositions = refTable.getSchema().getPrimaryKeyPositions();
			boolean matchFound = false;
			for (Tuple refTuple : refTable.getTuples()) {
				matchFound = true;
				for (int i=0; i<localKeyPositions.length; ++i) {
					if (!Tuple.valuesEqual(localKeyTypes[i], localKeyValues[i],
							refTuple.getValueAt(refKeyPositions[i]))) {
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
		
		// for each value, check if it complies with its attribute's type and constraint
		verifySchemaCompliance(newTuple);
		
		// check primary key uniqueness constraint
		verifyPrimaryKeyUniqueness(newTuple, tuples);
				
		// check foreign key referential integrity constraint
		// NOTE: new tuple cannot reference itself
		verifyForeignKeyConstraints(newTuple);
		
		tuples.add(newTuple);
	}
	
	
	
	
	public int deleteTuples(Exp condition) throws DatabaseException {
		
		if (condition == null) {
			int numDeleted = tuples.size();
			tuples.clear();
			return numDeleted;
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
		int numDeleted = tuples.size() - nextTuples.size();
		tuples = nextTuples;
		return numDeleted;
	}
	
	
	public int updateTuples(Exp condition, String[] updateAttrNames,
				Object[] updateValues) throws DatabaseException {
		
		// start a new tuples list
		List<Tuple> nextTuples = new ArrayList<Tuple>();
		
		// find each attribute position that's being updated
		int[] updatePositions = new int[updateAttrNames.length];
		for (int i=0; i<updatePositions.length; ++i) {
			Integer position = schema.getAttributePosition(updateAttrNames[i]);
			if (position == null) {
				throw new DatabaseException("Attribute '"+updateAttrNames[i]+
						"' does not exist.");
			}
			updatePositions[i] = position.intValue();
			for (int j=0; j<i; ++j) {
				if (updatePositions[j] == updatePositions[i]) {
					throw new DatabaseException("Attribute '"+updateAttrNames[i]+
							"' set multiple times.");
				}
			}
		}
		
		// make sure each update value complies with its attribute
		for (int i=0; i<updatePositions.length; ++i) {
			Object castedValue = AttrConstraintEvaluator.verifyValueComplies(
					updateValues[i], schema.getAttributes()[updatePositions[i]]);
			updateValues[i] = castedValue;
		}
		
		int numUpdated = 0;
		
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
				for (int i=0; i<updatePositions.length; ++i) {
					tCopy.setValueAt(updatePositions[i], updateValues[i]);
				}
				verifyPrimaryKeyUniqueness(tCopy, nextTuples);
				nextTuples.add(tCopy);
				numUpdated++;
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
		
		return numUpdated;
	}
	
	
	
	// constructor that reads from disk
	@SuppressWarnings("unchecked")
	protected Table(ObjectInputStream schemaOis, ObjectInputStream tuplesOis) 
			throws IOException, ClassNotFoundException {
		
		this.name = (String)schemaOis.readObject();
		this.schema = (Schema)schemaOis.readObject();
		this.tuples = (ArrayList<Tuple>)tuplesOis.readObject();

		// verify all constraints except referential constraints
		try {
			for (Tuple t : tuples)
				verifySchemaCompliance(t);
			System.out.println("Table '"+name+"': schema compliance met.");
			
			for (int i=0; i<tuples.size(); ++i) 
				verifyPrimaryKeyUniqueness(tuples.get(i), tuples.subList(0, i));
			System.out.println("Table '"+name+"': primary key uniqueness constraint met.");
			
		} catch (DatabaseException e) {
			throw new IOException("Table non-referential constraints not met. Possible corruption in disk.");
		}
	}
	// call this after all database tables have been read from disk
	protected void verifyForeignKeyConstraints() throws IOException {
		try {
			for (Tuple t: tuples)
				verifyForeignKeyConstraints(t);
			System.out.println("Table '"+name+"': referential integrity constraint met.");
		} catch (DatabaseException e) {
			throw new IOException("Table referential constraints not met. Possible corruption in disk.");
		}
	}
	
	// write to disk
	protected void writeToDisk(ObjectOutputStream schemaOos, ObjectOutputStream tuplesOos)
			throws IOException {
		schemaOos.writeObject(name);
		schemaOos.writeObject(schema);
		tuplesOos.writeObject(tuples);
	}
	

	
	/*
	public void print() {
		schema.printColumnHeaders();
		for (Tuple t : tuples) {
			t.print(schema);
		}
	}*/
	
	
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

