package database;

import java.io.*;
import java.util.*;

import ast.*;
import exception.DatabaseException;

public class Schema implements Serializable {

	private static final long serialVersionUID = -6742241104997327934L;


	public class ForeignKey implements Serializable {
		
		private static final long serialVersionUID = 144383316084225386L;
		
		private int[] foreignKeyPositions;	// order corresponds to order of attributes in referenced primary key
		private String refTableName;
		
		public ForeignKey(CreateCommand.ForeignKeyDescriptor
			foreignKeyDescriptor) throws DatabaseException {
			
			refTableName = foreignKeyDescriptor.getRefTableName();
			List<String> refAttrNames = foreignKeyDescriptor.getRefAttrNames();
			List<String> localAttrNames = foreignKeyDescriptor.getLocalAttrNames();

			// create map of ref attr names to local attr names
			if (refAttrNames.size() != localAttrNames.size()) {
				throw new DatabaseException("Number of foreign key attributes does not match "+
						"number of referenced primary key attributes.");
			}
			Map<String, String> refToLocalMap = new HashMap<String, String>();
			for (int i=0; i<refAttrNames.size(); ++i) {
				String refAttrName = refAttrNames.get(i);
				String localAttrName = localAttrNames.get(i);
				if (refToLocalMap.containsKey(refAttrName)) {
					throw new DatabaseException("'"+refAttrName+
							"' appears multiple times in referenced primary key.");
				}
				for (String s : refToLocalMap.values()) {
					if (s.equals(localAttrName)) {
						throw new DatabaseException("'"+localAttrName+
								"' appears multiple times in foreign key.");
					}
				}
				refToLocalMap.put(refAttrName, localAttrName);
			}
			
			Database.verifyExist(refTableName);
			
			Schema refSchema = Database.getTable(refTableName).getSchema();
			int[] refKeyPositions = refSchema.getPrimaryKeyPositions();
			if (refToLocalMap.size() != refKeyPositions.length) {
				throw new DatabaseException("Number of foreign key attributes does not match "+
						"number of primary key attributes in referenced table '"+refTableName+"'.");
			}
			
			// find the local attr positions corresponding to each key attribute of the
			// referenced table
			
			foreignKeyPositions = new int[refKeyPositions.length];	
			//for (Integer pos : refSchema.getPrimaryKeyPositions()) {
			for (int i=0; i<refKeyPositions.length; ++i) {
				
				Attribute refAttr = refSchema.getAttribute(refKeyPositions[i]);
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
		public String getRefTableName() {
			return refTableName;
		}
		public Table getRefTable() {
			return Database.getTable(refTableName);
		}
	}
	
	
	private Attribute[] attributes;
	private transient Map<String, Integer> attributesMap;	// maps attr name to its position
	
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
		updateAttributesMap();
		
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
			for (int j=0; j<i; j++) {
				if (primaryKeyPositions[j] == position) {
					throw new DatabaseException("'"+primaryKeyAttrName+
							"' appears multiple times in primary key.");
				}
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
	
	
	private void updateAttributesMap() {
		// generate map based on attributes
		attributesMap = new HashMap<String, Integer>();
		for (int i=0; i<attributes.length; ++i) {
			attributesMap.put(attributes[i].getName(), i);
		}
	}
	
	
	protected void printColumnHeaders() {
		Attribute.printColumnHeaders(attributes);
	}
	
	
	public void printDescription() {
		for (Attribute a : attributes){
			System.out.print(a.getName());		// name
			switch(a.getType()) {				// type
			case INT:
				System.out.print(" -- int");
				break;
			case DECIMAL:
				System.out.print(" -- decimal");
				break;
			case CHAR:
				System.out.print(" -- char("+a.getLength()+")");
				break;
			}
			for (int i=0; i<primaryKeyPositions.length; ++i) {	// primary key
				if (i==a.getPosition()) {
					System.out.print(" -- primary key");
					break;
				}
			}
			for (ForeignKey fk : foreignKeys) {		// foreign key
				int[] foreignKeyPositions = fk.getForeignKeyPositions();
				for (int i=0; i<foreignKeyPositions.length; ++i) {
					if (foreignKeyPositions[i]==a.getPosition()) {
						Table refTable = fk.getRefTable();
						Schema refSchema = refTable.getSchema();
						Attribute refKeyAttr = refSchema.getAttribute(
								refSchema.getPrimaryKeyPositions()[i]);
						System.out.print(" -- foreign key references "+
								refTable.getName()+"("+refKeyAttr.getName()+")");
					}
				}
			}
			Exp constraint = a.getConstraint();
			if (constraint != null) {
				System.out.print(" -- "+constraint.getExpString());
			}
			System.out.println("");
		}
	}
	
	
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}
	private void readObject(ObjectInputStream ois) throws IOException, 
								ClassNotFoundException {
		ois.defaultReadObject();
		updateAttributesMap();
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
