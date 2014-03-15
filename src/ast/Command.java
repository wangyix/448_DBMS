package ast;

import parser.Token;

public class Command extends ASTNode {
	public Command(Token tok) { super(tok); }
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
