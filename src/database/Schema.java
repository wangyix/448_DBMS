package database;

import java.util.*;

import ast.CreateTableCommand;
import exception.DatabaseException;

public class Schema {
	
	public class ForeignKey{
		
		private List<Integer> foreignKeyPositions;	// order corresponds to order of attributes in referenced primary key
		private Table refTable;
		
		public ForeignKey(CreateTableCommand.ForeignKeyDescriptor
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
			
			if (refToLocalMap.size() != refSchema.getAttributes().size()) {
				throw new DatabaseException("Number of foreign key attributes does not match "+
						"actual number of primary key attributes in referenced table '"+refTableName+"'.");
			}
			
			// find the local attr positions corresponding to each key attribute of the
			// referenced table
			foreignKeyPositions = new ArrayList<Integer>();	
			for (Integer pos : refSchema.getPrimaryKeyPositions()) {
				
				String refAttrName = refSchema.getAttribute(pos).getName();
				
				String localAttrName = refToLocalMap.get(refAttrName);
				if (localAttrName == null) {
					throw new DatabaseException("No local attribute is referencing primary key attribute '"+
							refAttrName+"' of referenced table '"+refTableName+"'.");
				}
				
				Integer localAttrPosition = attributesMap.get(localAttrName);
				if (localAttrPosition == null) {
					throw new DatabaseException("Local attribute '"+localAttrName+"' does not exist.");
				}
				foreignKeyPositions.add(localAttrPosition);
			}
		}
		public List<Integer> getForeignKeyPositions() {
			return foreignKeyPositions;
		}
		public Table getRefTable() {
			return refTable;
		}
	}
	
	
	private List<Attribute> attributes;
	private Map<String, Integer> attributesMap;	// maps attr name to its position
	
	private List<Integer> primaryKeyPositions;
	private List<ForeignKey> foreignKeys;
	
	
	public Schema(CreateTableCommand command) throws DatabaseException {

		this.attributes = new ArrayList<Attribute>();
		int i = 0;
		for (CreateTableCommand.AttributeDescriptor ad : command.getAttributeDescriptors()) {
			// attribute constructor does checks on its constraint
			attributes.add(new Attribute(i, ad));
			i++;
		}
		
		// generate map based on attributes
		attributesMap = new HashMap<String, Integer>();
		for (Attribute a : this.attributes) {
			attributesMap.put(a.getName(), a.getPosition());
		}
		
		// generate primaryKeyPositions based on primaryKeyAttrNames
		this.primaryKeyPositions = new ArrayList<Integer>();
		for (String s : command.getPrimaryKeyAttrNames()) {
			Integer position = attributesMap.get(s);
			if (position == null) {
				throw new DatabaseException("Primary key attribute '"+s+"' does not exist.");
			}
			primaryKeyPositions.add(position);
		}
		
		foreignKeys = new ArrayList<ForeignKey>();
		for (CreateTableCommand.ForeignKeyDescriptor fkd : command.getForeignKeyDescriptors()) {
			foreignKeys.add(new ForeignKey(fkd));
		}
	}
	
	
	public void print() {
		int rowWidth = 0;
		for (int i=0; i<attributes.size(); ++i) {
			Attribute attribute = attributes.get(i);
			rowWidth += attribute.getPrintWidth();
			attribute.print();
			if (i != attributes.size()-1) {
				System.out.print(" ");
				rowWidth++;
			}
		}
		System.out.println("");
		for (int i=0; i<rowWidth; ++i) {
			System.out.print("-");
		}
		System.out.println("");
	}
	
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public Attribute getAttribute(String attrName) {
		return attributes.get(attributesMap.get(attrName));
	}

	public Attribute getAttribute(int position) {
		return attributes.get(position);
	}
	
	public int getAttributePosition(String attrName) {
		return attributesMap.get(attrName);
	}
	
	public List<Integer> getPrimaryKeyPositions() {
		return primaryKeyPositions;
	}


	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}
}
