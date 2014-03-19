package database;

import java.io.*;

import exception.DatabaseException;
import parser.ParseException;
import parser.SQLParser;
import ast.*;
import astvisitor.AttrConstraintTypeChecker;

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
	
	private transient int printWidth;
	
	
	public Attribute(int position, CreateCommand.AttributeDescriptor attrDescriptor)
			throws DatabaseException {
		
		// make sure constraint references this attribute but not others
		// also make sure this constraint evaluates to a boolean
		AttrConstraintTypeChecker.check(attrDescriptor);
		
		this.position = position;
		this.name = attrDescriptor.getName();
		this.type = attrDescriptor.getType();
		this.length = attrDescriptor.getLength();
		this.constraint = attrDescriptor.getConstraint();
		
		computePrintWidth();
	}
	
	
	private void computePrintWidth() {
		switch (type) {		// compute print width
		case INT:
		case DECIMAL:
			printWidth = 10;
			break;
		case CHAR:
			printWidth = length;
			break;
		}
		printWidth = Math.max(printWidth, name.length());
	}
	
	public void print() {
		switch (type) {
		case INT:
		case DECIMAL:
			// right justify
			System.out.format("%"+printWidth+"s", name);
			break;
		case CHAR:
			// left justify
			System.out.format("%-"+printWidth+"s", name);
			break;
		}
	}
	
	public static void printColumnHeaders(Attribute[] attributes) {
		int rowWidth = 0;
		for (int i=0; i<attributes.length; ++i) {
			Attribute attribute = attributes[i];
			rowWidth += attribute.getPrintWidth();
			attribute.print();
			if (i != attributes.length-1) {
				System.out.print(" ");
				rowWidth++;
			}
		}
		System.out.println("");
		for (int i=0; i<rowWidth; ++i) {
			System.out.print("-");
		}
		System.out.println("");
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
		computePrintWidth();
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
	
	public int getPrintWidth() {
		return printWidth;
	}
}
