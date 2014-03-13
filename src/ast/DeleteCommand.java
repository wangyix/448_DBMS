package ast;

import parser.Token;

public class DeleteCommand extends Command {
	String table;
	Exp conditions;
	
	public DeleteCommand(Token tok, String table, Exp conditions) {
		super(tok);
		this.table = table;
		this.conditions = conditions;
	}
	
	public String getTable() { return table; }
	public Exp getConditions() { return conditions; }
}
