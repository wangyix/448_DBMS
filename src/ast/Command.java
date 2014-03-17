package ast;

import exception.DatabaseException;
import parser.Token;

public class Command extends ASTNode {
	public Command(Token tok) { super(tok); }
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
