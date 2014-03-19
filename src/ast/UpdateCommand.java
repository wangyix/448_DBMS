package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class UpdateCommand extends Command {
	
	public static class UpdateDescriptor {
	    String attrName;
		Exp value;
	    public UpdateDescriptor(String attrName, Exp value) {
	        this.attrName= attrName;
	        this.value = value;
	    }	    
	    public String getAttrName() {
	    	return attrName;
	    }
	    public Exp getValue() {
	    	return value;
	    }
	}
	
	private String tableName;
	private List<UpdateDescriptor> updateDescriptors;
	private Exp condition;
	
	public UpdateCommand(Token tok, String tableName, List<UpdateDescriptor> assignments, Exp condition) {
		super(tok);
		this.tableName = tableName;
		this.updateDescriptors = assignments;
		this.condition = condition;
	}
	
	public String getTableName(){
		return tableName;
	}
	public List<UpdateDescriptor> getUpdateDescriptors() {
		return updateDescriptors;
	}
	public Exp getCondition() {
		return condition;
	}
	
	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
