package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class DeleteSubschemaCommand extends Command {
	
	private String tableName;
	
	public DeleteSubschemaCommand(Token tok, String tableName) {
		super(tok);
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
