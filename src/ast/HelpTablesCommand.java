package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class HelpTablesCommand extends Command {
	public HelpTablesCommand(Token tok) {
		super(tok);
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
