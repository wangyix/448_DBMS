package ast;

import exception.DatabaseException;
import parser.Token;

public class DropTableCommand extends Command {
	private String table;
	
	public DropTableCommand(Token tok, String table) {
		super(tok);
		this.table = table;
	}
	
	public String getTable() { return table; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
