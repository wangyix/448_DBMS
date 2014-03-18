package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class InsertCommand extends Command {
	private String tableName;
	private List<Exp> values;
	
	public InsertCommand(Token tok, String tableName, List<Exp> values) {
		super(tok);
		this.tableName = tableName;
		this.values = values;
	}
	
	public String getTableName() { return tableName; }
	public List<Exp> getValues() { return values; }
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
