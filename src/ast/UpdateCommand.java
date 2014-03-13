package ast;

import java.util.*;

import parser.Token;

public class UpdateCommand extends Command {
	String table;
	List<AssignExp> assignments;
	Exp conditions;
	
	public UpdateCommand(Token tok, String table, List<AssignExp> assignments, Exp conditions) {
		super(tok);
		this.table = table;
		this.assignments = assignments;
		this.conditions = conditions;
	}
	
	public String getTable() { return table; }
	public List<AssignExp> getAssignments() { return assignments; }
	public Exp getConditions() { return conditions; }
}
