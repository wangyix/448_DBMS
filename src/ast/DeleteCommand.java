package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class DeleteCommand extends Command {
	private String tableName;
	private Exp condition;
	
	public DeleteCommand(Token tok, String tableName, Exp condition) {
		super(tok);
		this.tableName = tableName;
		this.condition = condition;
	}
	
	public String getTableName() { return tableName; }
	public Exp getCondition() { return condition; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
