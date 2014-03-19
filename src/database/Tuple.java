package database;

import java.io.*;
import java.util.*;


public class Tuple implements Serializable {

	private static final long serialVersionUID = 9016548390245302552L;
	
	//private Table parentTable;
	private Object[] values;
	
	public Tuple(List<Object> valuesList) {
		values = valuesList.toArray();
	}
	
	public Tuple(Object[] values) {
		this.values = values;
	}
	
	public void print(Schema parentSchema) {
		print(parentSchema.getAttributes());
	}
	
	public void print(Attribute[] attributes) {
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
	
	public Object[] getValues() {
		return values;
	}
	
	public Object getValueAt(int position) {
		return values[position];
	}
}
