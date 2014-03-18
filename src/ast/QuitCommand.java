package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class QuitCommand extends Command {
	public QuitCommand(Token tok) {
		super(tok);
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
