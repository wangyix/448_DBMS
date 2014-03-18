package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class DropTableCommand extends Command {
	private String tableName;
	
	public DropTableCommand(Token tok, String tableName) {
		super(tok);
		this.tableName = tableName;
	}
	
	public String getTableName() { return tableName; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
