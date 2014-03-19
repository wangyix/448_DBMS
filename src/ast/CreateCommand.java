package ast;

import java.util.*;

import astvisitor.ASTVisitor;
import database.Attribute;
import exception.DatabaseException;
import parser.Token;

public class CreateCommand extends Command {
	
	public static class ForeignKeyDescriptor {
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
	
	public static class AttributeDescriptor {
		private String name;
		private Attribute.Type type;
		private int length;			// only used for type CHAR
		private Exp constraint;
		public AttributeDescriptor(String name, Attribute.Type type,
				int length, Exp constraint) {
			this.name = name;
			this.type = type;
			this.length = length;
			this.constraint = constraint;
		}
		public String getName() {
			return name;
		}
		public Attribute.Type getType() {
			return type;
		}
		public int getLength() {
			return length;
		}
		public Exp getConstraint() {
			return constraint;
		}
	}
	
	
	private String tableName;
	private List<AttributeDescriptor> attributeDescriptors;
	private List<String> primaryKeyAttrNames;
	private List<ForeignKeyDescriptor> foreignKeyDescriptors;
		
	
	public CreateCommand(Token tok, String tableName,
			List<AttributeDescriptor> attributeDescriptors, 
			List<String> primaryKeyAttrNames,
			List<ForeignKeyDescriptor> foreignKeyDescriptors) {
		super(tok);
		this.tableName = tableName;
		this.attributeDescriptors = attributeDescriptors;
		this.primaryKeyAttrNames = primaryKeyAttrNames;
		this.foreignKeyDescriptors = foreignKeyDescriptors;
	}

	
	
	public String getTableName() {
		return tableName;
	}
	public List<AttributeDescriptor> getAttributeDescriptors() {
		return attributeDescriptors;
	}
	public List<String> getPrimaryKeyAttrNames() {
		return primaryKeyAttrNames;
	}
	public List<ForeignKeyDescriptor> getForeignKeyDescriptors() {
		return foreignKeyDescriptors;
	}

	public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
