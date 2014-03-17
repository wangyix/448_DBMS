package database;

import java.io.*;

import parser.ParseException;
import parser.SQLParser;
import ast.*;

public class Attribute implements Serializable{

	private static final long serialVersionUID = 8436132631907741022L;

	public enum Type {
		INT, CHAR, DECIMAL;
	}
	
	private int position;
	private String name;
	private Type type;
	private int length;					// only used for type CHAR
	private transient Exp constraint;	// cannot reference other attributes
	
	
	
	public Attribute(int position, String name, Type type, Exp constraint) {
		this(position, name, type, -1, constraint);
	}
	
	public Attribute(int position, String name, Type type, int length, Exp constraint) {
		this.position = position;
		this.name = name;
		this.type = type;
		this.length = length;
		this.constraint = constraint;
	}
	
	
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		if (constraint != null)
			// constraint stored as its exp string
			oos.writeObject(constraint.getExpString());
		else
			oos.writeObject("");
	}
	private void readObject(ObjectInputStream ois) throws IOException, 
								ClassNotFoundException {
		ois.defaultReadObject();
		
		String expString = (String)ois.readObject();
		if (expString.length()==0) {
			constraint = null;
		} else {
			InputStream is = new ByteArrayInputStream(expString.getBytes());
			try {
				constraint = (new SQLParser(is)).ExpressionFromDisk();
			} catch (ParseException e) {
				throw new IOException("Attribute constraint expression corrupted in disk.");
			}
		}
	}
	
	public int getPosition() {
		return position;
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
