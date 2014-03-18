package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class LiteralExp extends Exp {
	
	private Object value;
	
	public LiteralExp(Token tok, Object value) {
		super(tok);
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public static String processStringLiteral(String literal) {
		StringBuilder sb = new StringBuilder(literal.length());
		char[] chars = literal.toCharArray();
		for (int i=1; i<chars.length-1; ++i) {	// ignore surrounding single quotes
			if (chars[i]=='\\' || chars[i]=='\'') {
				sb.append('\'');
				i++;	// skip next char
			}
			else {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
