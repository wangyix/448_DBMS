package database;

import java.io.*;
import java.util.*;


public class Tuple implements Serializable {

	private static final long serialVersionUID = 9016548390245302552L;
	
	private Object[] values;
	
	public Tuple(List<Object> valuesList) {
		values = valuesList.toArray();
	}
	
	public Tuple(Tuple t) {		// shallow copy
		Object[] tValues = t.getValues();
		values = new Object[tValues.length];
		for (int i=0; i<values.length; ++i) {
			values[i] = tValues[i];
		}
	}
	
	public Tuple(Object[] values) {
		this.values = values;
	}
	
	protected void print(Schema parentSchema) {
		print(parentSchema.getAttributes());
	}
	
	protected void print(Attribute[] attributes) {
		for (int i=0; i<values.length; ++i) {
			Attribute attribute = attributes[i];
			int printWidth = attribute.getPrintWidth();
			Object value = values[i];
			switch (attribute.getType()) {
			case INT:		// right justify
				System.out.format("%"+printWidth+"d", (int)value);
				break;
			case DECIMAL:	// right justify
				String doubleString = String.format("%"+printWidth+".4f",
						(double)value);
				if (doubleString.length() > printWidth)
					System.out.format("%"+printWidth+".3e", (double)value);
				else
					System.out.print(doubleString);
				break;
			case CHAR:		// left justify
				System.out.format("%-"+printWidth+"s", (String)value);
				break;
			}
			if (i != values.length-1)
				System.out.print(" ");
		}
		System.out.println("");
	}
	
	
	// assumes values are same type
	protected static boolean valuesEqual(Attribute.Type type,
			Object value1, Object value2) {
		switch (type) {
		case INT:
			return (((Integer)value1).intValue() ==
					((Integer)value2).intValue());
		case DECIMAL:
			return (((Double)value1).doubleValue() ==
					((Double)value2).doubleValue());
		case CHAR:
			return ((String)value1).equals((String)value2);
		}
		return false;
	}
	
	
	// assumes value passes attribute constraint
	protected void setValueAt(int position, Object value) {
		values[position] = value;
	}
	
	public Object[] getValues() {
		return values;
	}
	
	public Object getValueAt(int position) {
		return values[position];
	}
}
