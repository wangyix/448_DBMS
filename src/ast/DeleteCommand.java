package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class DeleteCommand extends Command {
	private String tableName;
	private Exp conditions;
	
	public DeleteCommand(Token tok, String tableName, Exp conditions) {
		super(tok);
		this.tableName = tableName;
		this.conditions = conditions;
	}
	
	public String getTableName() { return tableName; }
	public Exp getConditions() { return conditions; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
