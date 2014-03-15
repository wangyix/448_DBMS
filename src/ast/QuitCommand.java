package ast;

import parser.Token;

public class QuitCommand extends Command {
	public QuitCommand(Token tok) {
		super(tok);
	}
	
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
