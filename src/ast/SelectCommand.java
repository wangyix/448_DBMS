package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class SelectCommand extends Command {
	private List<AttributeExp> attributes;	// null means SELECT *
	private List<String> tables;
	private Exp conditions;					// null means select all rows
	
	public SelectCommand(Token tok, List<AttributeExp> attributes, List<String> tables, Exp conditions) {
		super(tok);
		this.attributes = attributes;
		this.tables = tables;
		this.conditions = conditions;
	}
	
	public List<AttributeExp> getAttributes() {
		return attributes;
	}

	public List<String> getTables() {
		return tables;
	}

	public Exp getConditions() {
		return conditions;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
