package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class UpdateCommand extends Command {
	private String table;
	private List<AttributeAssign> assignments;
	private Exp conditions;
	
	public UpdateCommand(Token tok, String table, List<AttributeAssign> assignments, Exp conditions) {
		super(tok);
		this.table = table;
		this.assignments = assignments;
		this.conditions = conditions;
	}
	
	public String getTable() { return table; }
	public List<AttributeAssign> getAssignments() { return assignments; }
	public Exp getConditions() { return conditions; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
