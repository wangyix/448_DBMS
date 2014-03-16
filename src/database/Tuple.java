package database;

import java.io.*;
import java.util.*;

import value.*;

public class Tuple implements Serializable {

	private static final long serialVersionUID = 9016548390245302552L;
	
	private List<Value> values;
	
	public Tuple() {
		values = new ArrayList<Value>();
	}
	
	public void append(Value v) {
		values.add(v);
	}
	
	public List<Value> getValues() {
		return values;
	}
}
