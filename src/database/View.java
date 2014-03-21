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
	
	public int print() {
		if (attributes.length == 0) {
			System.out.println("No columns selected.");
			return 0;
		}
		Attribute.printColumnHeaders(attributes);
		for (int i=0; i<tuples.size(); ++i) {
			if (i%10 == 0 && i != 0) {
				System.out.println("");
				Attribute.printColumnHeaders(attributes);
			}
			tuples.get(i).print(attributes);
		}
		return tuples.size();
	}
}
