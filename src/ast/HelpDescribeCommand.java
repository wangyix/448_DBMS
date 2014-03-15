package ast;

import parser.Token;

public class HelpDescribeCommand extends Command {
	String table;
	
	public HelpDescribeCommand(Token tok, String table) {
		super(tok);
		this.table = table;
	}
	
	public String getTable() { return table; }
	
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}