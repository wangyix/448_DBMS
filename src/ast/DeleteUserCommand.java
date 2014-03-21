package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class DeleteUserCommand extends Command {
	
	private String userName;
	
	public DeleteUserCommand(Token tok, String userName) {
		super(tok);
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
