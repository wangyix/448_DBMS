package database;

import java.io.*;
import java.util.*;


public class Tuple implements Serializable {

	private static final long serialVersionUID = 9016548390245302552L;
	
	private Table parentTable;
	private List<Object> values;
	
	public Tuple(Table parentTable) {
		this.parentTable = parentTable;
		values = new ArrayList<Object>();
	}
	
	
	public void print() {
		Schema schema = parentTable.getSchema();
		for (int i=0; i<values.size(); ++i) {
			Attribute attribute = schema.getAttribute(i);
			int printWidth = attribute.getPrintWidth();
			Object value = values.get(i);
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
			if (i != values.size()-1)
				System.out.print(" ");
		}
		System.out.println("");
	}
	
	public void append(Object v) {
		values.add(v);
	}
		
	public Table getParentTable() {
		return parentTable;
	}
	
	public List<Object> getValues() {
		return values;
	}
	
	public Object getValueAt(int position) {
		return values.get(position);
	}
}
