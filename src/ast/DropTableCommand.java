package ast;

import parser.Token;

public class DropTableCommand extends Command {
	String table;
	
	public DropTableCommand(Token tok, String table) {
		super(tok);
		this.table = table;
	}
	
	public String getTable() { return table; }
}
