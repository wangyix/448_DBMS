package database;

import java.util.*;

import exception.DatabaseException;

public class Schema {
	
	public static class ForeignKey{
		private List<Integer> foreignKeyPositions;	// order corresponds to order of attributes in referenced primary key
		private Table refTable;
		
		public ForeignKey(Table refTable, List<Integer> foreignKeyPositions) {
			this.refTable = refTable;
			this.foreignKeyPositions = foreignKeyPositions;
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
	
	
	public Schema(List<Attribute> attributes, List<String> primaryKeyAttrs,
			List<String> primaryKeyAttrNames) throws DatabaseException {
	
		this.attributes = attributes;
		
		// generate map based on attributes
		attributesMap = new HashMap<String, Integer>();
		for (Attribute a : this.attributes) {
			attributesMap.put(a.getName(), a.getPosition());
		}
		
		// generate primaryKeyPositions based on primaryKeyAttrNames
		this.primaryKeyPositions = new ArrayList<Integer>();
		for (String s : primaryKeyAttrNames) {
			Integer position = attributesMap.get(s);
			if (position == null) {
				throw new DatabaseException("Primary key attribute '"+s+"' does not exist.");
			}
			primaryKeyPositions.add(position);
		}
		
		foreignKeys = new ArrayList<ForeignKey>();	// foreign keys added after construction
	}
	
	
	public Schema addForeignKeyLink(String refTableName, List<String> localAttrNames,
			List<String> refAttrNames) throws DatabaseException {
				
		// create map of ref attr names to local attr names
		if (refAttrNames.size() != localAttrNames.size()) {
			throw new DatabaseException("Number of foreign key attributes does not match "+
					"number of referenced primary key attributes.");
		}
		Map<String, String> refToLocalMap = new HashMap<String, String>();
		for (int i=0; i<refAttrNames.size(); ++i)
			refToLocalMap.put(refAttrNames.get(i), localAttrNames.get(i));
		
		
		Table refTable = Database.getTable(refTableName);
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
		List<Integer> foreignKeyPositions = new ArrayList<Integer>();	
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
		
		// add new foreign key constraint
		foreignKeys.add(new ForeignKey(refTable, foreignKeyPositions));
		
		return this;
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
	
	public List<Integer> getPrimaryKeyPositions() {
		return primaryKeyPositions;
	}


	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}
}
