package ast;

import exception.DatabaseException;
import parser.Token;

public class Exp extends ASTNode {
    
	private static StringBuilder sb = new StringBuilder();
	private String expString = null;
	
	public Exp(Token tok) {
		super(tok);
	}
    
	
	public static void clearGlobalExpString() {
		sb.setLength(0);
	}
	
	public static void appendToGlobalExpString(char c) {
		sb.append(c);
	}
	public static void appendToGlobalExpString(String s) {
		sb.append(s);
	}
	
	
	
	public void saveExpString() {
		expString = sb.toString();
	}
	
	public String getExpString() {
		return expString;
	}
	
    public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
