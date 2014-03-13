package ast;

import parser.Token;

public class AttributeDecl extends ASTNode {

	public enum Type {
		INT, CHAR, DECIMAL
	}
	
	String table;
	Type type;
	int length;		// only used for type INT
	String name;
	Exp constraint;
	
	public AttributeDecl(Token tok, String table, String name) {
		this(tok, table, null, -1, name, null);
	}
	
	public AttributeDecl(Token tok, String table, Type type, String name, Exp constraint) {
		this(tok, table, type, -1, name, constraint);
	}
	
	public AttributeDecl(Token tok, String table, Type type, int length, String name, Exp constraint) {
		super(tok);
		this.table = table;
		this.type = type;
		this.length = length;
		this.name = name;
		this.constraint = constraint;
	}
	
	public String getTable() {
		return table;
	}
	
	public AttributeDecl setTable(String table) {
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
