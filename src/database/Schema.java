package database;

import java.util.*;

import ast.CreateCommand;
import exception.DatabaseException;

public class Schema {
	
	public class ForeignKey{
		
		private int[] foreignKeyPositions;	// order corresponds to order of attributes in referenced primary key
		private Table refTable;
		
		public ForeignKey(CreateCommand.ForeignKeyDescriptor
			foreignKeyDescriptor) throws DatabaseException {
			
			String refTableName = foreignKeyDescriptor.getRefTableName();
			List<String> refAttrNames = foreignKeyDescriptor.getRefAttrNames();
			List<String> localAttrNames = foreignKeyDescriptor.getLocalAttrNames();

			// create map of ref attr names to local attr names
			if (refAttrNames.size() != localAttrNames.size()) {
				throw new DatabaseException("Number of foreign key attributes does not match "+
						"number of referenced primary key attributes.");
			}
			Map<String, String> refToLocalMap = new HashMap<String, String>();
			for (int i=0; i<refAttrNames.size(); ++i)
				refToLocalMap.put(refAttrNames.get(i), localAttrNames.get(i));
			
			
			refTable = Database.getTable(refTableName);
			if (refTable == null) {
				throw new DatabaseException("Referenced table '"+refTableName+"' does not exist.");
			}
			
			Schema refSchema = refTable.getSchema();
			int[] refSchemaPrimaryKeyPositions = refSchema.getPrimaryKeyPositions();
			if (refToLocalMap.size() != refSchemaPrimaryKeyPositions.length) {
				throw new DatabaseException("Number of foreign key attributes does not match "+
						"number of primary key attributes in referenced table '"+refTableName+"'.");
			}
			
			// find the local attr positions corresponding to each key attribute of the
			// referenced table
			
			foreignKeyPositions = new int[refSchemaPrimaryKeyPositions.length];	
			//for (Integer pos : refSchema.getPrimaryKeyPositions()) {
			for (int i=0; i<refSchemaPrimaryKeyPositions.length; ++i) {
				
				Attribute refAttr = refSchema.getAttribute(refSchemaPrimaryKeyPositions[i]);
				String refAttrName = refAttr.getName();
				
				String localAttrName = refToLocalMap.get(refAttrName);
				if (localAttrName == null) {
					throw new DatabaseException("No local attribute is referencing primary key attribute '"+
							refAttrName+"' of referenced table '"+refTableName+"'.");
				}
				
				Integer localAttrPosition = attributesMap.get(localAttrName);
				if (localAttrPosition == null) {
					throw new DatabaseException("Local attribute '"+localAttrName+"' does not exist.");
				}
				
				// type check referenced attr with local attr
				Attribute.Type refAttrType = refAttr.getType(); 
				Attribute.Type localAttrType = attributes[localAttrPosition].getType();
				if (refAttrType != localAttrType) {
					throw new DatabaseException("Type of local attribute '"+localAttrName+
							"' does not match that of referenced attribute '"+refAttrName+"'.");
				}
								
				foreignKeyPositions[i] = localAttrPosition;
			}
		}
		public int[] getForeignKeyPositions() {
			return foreignKeyPositions;
		}
		public Table getRefTable() {
			return refTable;
		}
	}
	
	
	private Attribute[] attributes;
	private Map<String, Integer> attributesMap;	// maps attr name to its position
	
	private int[] primaryKeyPositions;
	private ForeignKey[] foreignKeys;
	

	
	public Schema(CreateCommand command) throws DatabaseException {

		// create the attributes based on AttributeDescriptors
		List<CreateCommand.AttributeDescriptor> attrDescriptors = command.getAttributeDescriptors();
		attributes = new Attribute[attrDescriptors.size()];
		for (int i=0; i<attrDescriptors.size(); ++i) {
			attributes[i] = new Attribute(i, attrDescriptors.get(i));
		}
		
		// generate map based on attributes
		attributesMap = new HashMap<String, Integer>();
		for (int i=0; i<attributes.length; ++i) {
			attributesMap.put(attributes[i].getName(), i);
		}
		
		// generate primaryKeyPositions based on primaryKeyAttrNames
		List<String> primaryKeyAttrNames = command.getPrimaryKeyAttrNames();
		primaryKeyPositions = new int[primaryKeyAttrNames.size()];
		for (int i=0; i<primaryKeyPositions.length; ++i) {
			String primaryKeyAttrName = primaryKeyAttrNames.get(i);
			Integer position = attributesMap.get(primaryKeyAttrName);
			if (position == null) {
				throw new DatabaseException("Primary key attribute '"+
						primaryKeyAttrName+"' does not exist.");
			}
			primaryKeyPositions[i] = position;
		}
		
		List<CreateCommand.ForeignKeyDescriptor> foreignKeyDescriptors = 
				command.getForeignKeyDescriptors();
		foreignKeys = new ForeignKey[foreignKeyDescriptors.size()];
		for (int i=0; i<foreignKeys.length; ++i) {
			foreignKeys[i] = new ForeignKey(foreignKeyDescriptors.get(i));
		}
	}
	
	
	public void print() {
		Attribute.printColumnHeaders(attributes);
	}
	
	
	public Attribute[] getAttributes() {
		return attributes;
	}

	public Attribute getAttribute(String attrName) {
		Integer position = attributesMap.get(attrName);
		if (position == null)
			return null;
		return attributes[position];
	}

	public Attribute getAttribute(int position) {
		return attributes[position];
	}
	
	public Integer getAttributePosition(String attrName) {
		return attributesMap.get(attrName);
	}
	
	public int[] getPrimaryKeyPositions() {
		return primaryKeyPositions;
	}


	public ForeignKey[] getForeignKeys() {
		return foreignKeys;
	}
}
