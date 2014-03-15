package ast;

import parser.Token;

public class HelpTablesCommand extends Command {
	public HelpTablesCommand(Token tok) {
		super(tok);
	}
	
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
