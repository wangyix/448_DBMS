package ast;

import java.util.*;

import database.Attribute;
import parser.Token;

public class CreateTableCommand extends Command {
	
	public static class ForeignKeyDescriptor{
		private String refTableName;
		List<String> localAttrNames;
		List<String> refAttrNames;
		
		public ForeignKeyDescriptor(String refTableName, List<String> localAttrNames,
				List<String> refAttrNames) {
			this.refTableName = refTableName;
			this.localAttrNames = localAttrNames;
			this.refAttrNames = refAttrNames;
		}
		public String getRefTableName() {
			return refTableName;
		}
		public List<String> getLocalAttrNames() {
			return localAttrNames;
		}
		public List<String> getRefAttrNames() {
			return refAttrNames;
		}
	}
	
	private String tableName;
	private List<Attribute> attributes;
	private List<String> primaryKeyAttrNames;
	private List<ForeignKeyDescriptor> foreignKeyDescriptors;
		
	
	public CreateTableCommand(Token tok, String tableName, List<Attribute> attributes, 
			List<String> primaryKeyAttrNames, List<ForeignKeyDescriptor> foreignKeyDescriptors) {
		super(tok);
		this.tableName = tableName;
		this.attributes = attributes;
		this.primaryKeyAttrNames = primaryKeyAttrNames;
		this.foreignKeyDescriptors = foreignKeyDescriptors;
	}

	
	
	public String getTableName() {
		return tableName;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public List<String> getPrimaryKeyAttrNames() {
		return primaryKeyAttrNames;
	}
	public List<ForeignKeyDescriptor> getForeignKeyDescriptors() {
		return foreignKeyDescriptors;
	}

	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
