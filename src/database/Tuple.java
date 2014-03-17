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
