package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class CreateSubschemaCommand extends Command {
	
	private String tableName;
	private List<String> attrNames;
	
	public CreateSubschemaCommand(Token tok, String tableName, List<String> attrNames) {
		super(tok);
		this.tableName = tableName;
		this.attrNames = attrNames;
	}

	public String getTableName() {
		return tableName;
	}
	
	public List<String> getAttrNames() {
		return attrNames;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
