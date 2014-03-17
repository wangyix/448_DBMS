package ast;

import exception.DatabaseException;
import parser.Token;

public class DeleteCommand extends Command {
	private String table;
	private Exp conditions;
	
	public DeleteCommand(Token tok, String table, Exp conditions) {
		super(tok);
		this.table = table;
		this.conditions = conditions;
	}
	
	public String getTable() { return table; }
	public Exp getConditions() { return conditions; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
