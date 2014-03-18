package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class HelpCommandCommand extends Command {

	public enum Type {
		CREATE_TABLE, DROP_TABLE, SELECT, INSERT, DELETE, UPDATE
	}
	
	private Type type;
	
	public HelpCommandCommand(Token tok, Type type) {
		super(tok);
		this.type = type;
	}
	
	public Type getType() { return type; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
