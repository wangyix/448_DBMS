package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class SelectCommand extends Command {
	private List<String> attrNames;			// null means SELECT *
	private List<String> tableNames;
	private Exp condition;					// null means select all rows
	
	public SelectCommand(Token tok, List<String> attrNames, List<String> tableNames, Exp condition) {
		super(tok);
		this.attrNames = attrNames;
		this.tableNames = tableNames;
		this.condition = condition;
	}
	
	public List<String> getAttrNames() {
		return attrNames;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public Exp getcondition() {
		return condition;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
