package database;

import java.util.*;

public class View {

	private Attribute[] attributes;
	private List<Tuple> tuples;
	
	public View(Attribute[] attributes) {
		this.attributes = attributes;
		tuples = new ArrayList<Tuple>();
	}
	
	public void addTuple(Tuple newTuple) {
		tuples.add(newTuple);
	}
	
	public void print() {
		Attribute.printColumnHeaders(attributes);
		for (Tuple t : tuples) {
			t.print(attributes);
		}
	}
}
