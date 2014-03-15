package schema;

import java.io.*;
import java.util.EnumSet;

import parser.ParseException;
import parser.SQLParser;
import ast.*;

public class Attribute implements Serializable{

	private static final long serialVersionUID = 8436132631907741022L;

	public enum Type {
		INT, CHAR, DECIMAL;

		private static Type[] types;	// maps int to Type
		private int index;				// maps Type to int
		
		// assign index to each enum value, insert into types array
		static {
			types = new Type[3];
			int index = 0;
			for (Type t : EnumSet.allOf(Type.class)){
				t.setIndex(index);
				types[index] = t;
				index++;
			}
		}
		
		Type() {}
		private void setIndex(int i) { index = i; }
		
		// methods to convert between Type and int
		public int toInt() { return index; }
		public static Type intToType(int i) {
			return types[i];
		}
	}
	
	
	String table;
	Type type;
	int length;					// only used for type CHAR
	String name;
	transient Exp constraint;
	// constraint expression cannot contain other attributes
	
	
	public Attribute(String table, String name) {
		this(table, null, -1, name, null);
	}
	
	public Attribute(String table, Type type, String name, Exp constraint) {
		this(table, type, -1, name, constraint);
	}
	
	public Attribute(String table, Type type, int length, String name, Exp constraint) {
		this.table = table;
		this.type = type;
		this.length = length;
		this.name = name;
		this.constraint = constraint;
	}
	
	
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		if (constraint != null)
			oos.writeObject(constraint.getExpString());	// constraint stored as its lexeme
		else
			oos.writeObject("");
	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		
		String expString = (String)ois.readObject();
		if (expString.length()==0) {
			constraint = null;
		} else {
			InputStream is = new ByteArrayInputStream(expString.getBytes());
			try {
				constraint = (new SQLParser(is)).Expression();
			} catch (ParseException e) {
				throw new Error("Attribute constraint expString corrupted in disk");
			}
		}
	}
	
	
	public String getTable() {
		return table;
	}
	
	public Attribute setTable(String table) {
		this.table = table;
		return this;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public Exp getConstraint() {
		return constraint;
	}
}
