package ast;

import astvisitor.ASTVisitor;
import parser.Token;
import database.Users;
import exception.DatabaseException;

public class CreateUserCommand extends Command{

	private String userName;
	private Users.Type userType;
	
	public CreateUserCommand(Token tok, String userName, Users.Type userType) {
		super(tok);
		this.userName = userName;
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public Users.Type getUserType() {
		return userType;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
