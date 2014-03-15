package ast;

import java.util.*;

import parser.Token;

public class UpdateCommand extends Command {
	String table;
	List<AttributeAssign> assignments;
	Exp conditions;
	
	public UpdateCommand(Token tok, String table, List<AttributeAssign> assignments, Exp conditions) {
		super(tok);
		this.table = table;
		this.assignments = assignments;
		this.conditions = conditions;
	}
	
	public String getTable() { return table; }
	public List<AttributeAssign> getAssignments() { return assignments; }
	public Exp getConditions() { return conditions; }
	
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
