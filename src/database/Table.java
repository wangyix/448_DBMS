package database;

import java.util.*;

public class Table {
	
	private String name;
	private Schema schema;
	private List<Tuple> tuples;
	
	
	public String getName() {
		return name;
	}
	
	public Schema getSchema() {
		return schema;
	}
	
	public List<Tuple> getTuples() {
		return tuples;
	}
	
	// addTuple
	// contains check for schema compliance
}

