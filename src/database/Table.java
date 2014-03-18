package database;

import java.util.*;

import ast.Exp;
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
	
	
	public void addTuple(Tuple newTuple) throws DatabaseException {
		
		List<Attribute> attributes = schema.getAttributes();
		List<Object> values = newTuple.getValues();
		
		if (values.size() != attributes.size()) {
			throw new DatabaseException("Tuple does not have the correct number of values"+
					" as specified by the schema of table '"+name+"'.");
		}
		
		// for each schema attribute, type check and constraint check the corresponding
		// value in the tuple
		
		List<Tuple> referencedTuples = new ArrayList<Tuple>();	// used for constraint checking
		referencedTuples.add(newTuple);
		
		for (int i=0; i<attributes.size(); ++i) {
			
			Attribute attribute = attributes.get(i);
			
			// check compliance with attribute type
			
			Attribute.Type expectedType = attribute.getType();
			Object value = values.get(i);
			boolean typeCompatible = false;
			switch (expectedType) {
			case INT:
				if (value instanceof Integer)
					typeCompatible = true;
				break;
			case DECIMAL:
				if (value instanceof Integer || value instanceof Double)
					typeCompatible = true;
				break;
			case CHAR:
				if (value instanceof String) {
					String valueStr = (String)value;
					if (valueStr.length() > attribute.getLength()) {
						throw new DatabaseException("String at position "+i+" of tuple is longer than"+
								" expected by the schema of table '"+name+"'.");
					}
					typeCompatible = true;
				}
			}
			if (!typeCompatible) {
				throw new DatabaseException("Value at position "+i+" of tuple does not match"+
						" type expected by the schema of table '"+name+"'.");
			}
			
			// check compliance with attribute constraint
			
			Exp constraint = attribute.getConstraint();
			if (constraint != null) {
				
				boolean constraintComply = (boolean)ExpEvaluator.evaluate(constraint, referencedTuples);
				if (!constraintComply) {
					throw new DatabaseException("Value at position "+i+" of tuple failed"+
							" its attribute constraint imposed by the schema of table '"+name+"'.");
				}
			}
		}
		
		
		// check primary key uniqueness constraint
		
		for (Tuple tuple : tuples) {
			
			boolean keyIsDifferent = false;
			
			for (Integer position : schema.getPrimaryKeyPositions()) {
				Attribute.Type keyAttrType = schema.getAttribute(position).getType();
				Object value = tuple.getValueAt(position);
				Object newValue = newTuple.getValueAt(position);
				if (keyAttrType == Attribute.Type.INT) {
					if (((int)value) != (int)newValue) {
						keyIsDifferent = true;
						break;
					}
				} else if (keyAttrType == Attribute.Type.DECIMAL) {
					if (((double)value) != (double)newValue) {
						keyIsDifferent = true;
						break;
					}
				} else {
					if (!((String)value).equals((String)newValue)) {
						keyIsDifferent = true;
						break;
					}
				}
			}
			if (!keyIsDifferent) {
				throw new DatabaseException("New tuple violates primary key uniqueness constraint of table '"
						+name+"'.");
			}
		}
		
		tuples.add(newTuple);
	}
	
	
	
	public void print() {
		schema.print();
		for (Tuple t : tuples) {
			t.print();
		}
	}
}

