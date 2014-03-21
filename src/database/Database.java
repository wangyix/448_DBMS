package database;

import java.io.*;
import java.util.*;

import ast.Exp;
import astvisitor.ExpEvaluator;
import exception.DatabaseException;


public class Database {
	
	private static final String schemaFileName = "schemas.ser";
	private static final String tuplesFileName = "tuples.ser";
	
	
	private static Map<String, Table> tablesMap
			= new HashMap<String, Table>();			// global
	
	
	// set attrNames null for SELECT *, condition null if no WHERE clause
	public static View createView(String[] attrNames, String[] tableNames, Exp condition)
			throws DatabaseException {
		
		Table[] tables;
		Schema[] schemas;			// ith entry is schema of table i
		Tuple[] currentTupleCombo;	// ith entry is the tuple from table i
		
		Attribute[] attributes;
		int[] attrTableIndex;	// ith entry is ith attribute's parent table's index in tables[]
		
		
		// find each table referenced in the WHERE clause, store its schema
		tables = new Table[tableNames.length];
		schemas = new Schema[tables.length];
		for (int i=0; i<tables.length; ++i) {
			String tableName = tableNames[i];
			Database.verifyExist(tableName);
			tables[i] = Database.getTable(tableName);
			schemas[i] = tables[i].getSchema();
		}
		
		// find and record the table and column-position of each selected attribute
		// add all attributes from all tables if SELECT *
		// otherwise, only add attributes specified in the SELECT
		
		if (attrNames == null) {
			int totalNumAttributes = 0;
			for (Schema s : schemas) 
				totalNumAttributes += s.getNumVisibleAttributes();
			
			attributes = new Attribute[totalNumAttributes];
			attrTableIndex = new int[attributes.length];
			
			int attributeIndex = 0;
			for (int tableIndex=0; tableIndex<tables.length; ++tableIndex) {
				Attribute[] schemaAttributes = schemas[tableIndex].getVisibleAttributes();
				for (Attribute a : schemaAttributes) {
					attributes[attributeIndex] = a;
					attrTableIndex[attributeIndex] = tableIndex;
					attributeIndex++;
				}
			}
			
		} else {
			attributes = new Attribute[attrNames.length];
			attrTableIndex = new int[attributes.length];		
			for (int i=0; i<attributes.length; ++i) {
				String attrName = attrNames[i];
				boolean attrFound = false;
				for (int tableIndex=0; tableIndex<tables.length; ++tableIndex) {
					 Attribute attribute = schemas[tableIndex].getVisibleAttribute(attrName);
					if (attribute != null) {
						if (!attrFound) {
							attributes[i] = attribute;
							attrTableIndex[i] = tableIndex;
							attrFound = true;
						} else {
							throw new DatabaseException("Attribute '"+attrName+
									"' found in multiple tables.");
						}
					}
				}
				if (!attrFound) {
					throw new DatabaseException("Attribute '"+attrName+"' not found "+
							"in any of the specified tables.");
				}
			}
		}
		
		// check every combo of choosing one tuple from each table,
		// construct a view
		
		View view = new View(attributes);
				
		// fill currentTupleCombo with row 0 from all tables
		currentTupleCombo = new Tuple[tables.length];
		for (int i=0; i<tables.length; ++i) {
			tables[i].resetIterator();
			if (!tables[i].hasNext()) {	// if any table empty, leave view empty
				return view;
			}
			currentTupleCombo[i] = tables[i].getNextTuple();
		}
		// iterate thru all combinations of rows
		int tableIndexToIncrement;
		do {
			
			// check current combination of rows
			if (ExpEvaluator.evaluateCondition(
					condition, currentTupleCombo, schemas)) {
				Object[] newValues = new Object[attributes.length];
				for (int j=0; j<newValues.length; ++j) {
					newValues[j] = currentTupleCombo[attrTableIndex[j]]		// tuple
							.getValueAt(attributes[j].getPosition());		// value
				}
				view.addTuple(new Tuple(newValues));
			}
			
			// increment currentTupleCombo to next combination
			tableIndexToIncrement = tables.length-1;
			while (tableIndexToIncrement >= 0) {
				Table table = tables[tableIndexToIncrement];
				if (table.hasNext()) {
					currentTupleCombo[tableIndexToIncrement] = table.getNextTuple();
					break;
				}
				else {
					table.resetIterator();
					currentTupleCombo[tableIndexToIncrement] = table.getNextTuple();
					tableIndexToIncrement--;
				}
			}
		} while (tableIndexToIncrement >= 0);
		
		return view;
	}
	
	
	
	public static void dropTable(String tableName) throws DatabaseException {
		
		verifyExist(tableName);
		Table deleteTable = tablesMap.get(tableName);
		
		// make sure no other tables reference this table
		for (Table table : tablesMap.values()) {
			
			if (table==deleteTable)	// we don't care if this table references itself
				continue;
			
			Schema.ForeignKey[] foreignKeys = table.getSchema().getForeignKeys();
			for (Schema.ForeignKey fk : foreignKeys) {
				String refTableName = fk.getRefTableName();
				if (refTableName.equals(deleteTable.getName())) {
					throw new DatabaseException("Table '"+table.getName()+"' references this table.");
				}
			}
		}
		
		tablesMap.remove(tableName);
	}
	
	
	public static void writeTablesToDisk() throws FileNotFoundException, IOException {
		ObjectOutputStream schemaOos = new ObjectOutputStream(new FileOutputStream(schemaFileName));
		ObjectOutputStream tuplesOos = new ObjectOutputStream(new FileOutputStream(tuplesFileName));
		schemaOos.writeObject(tablesMap.size());
		for (Table table : tablesMap.values()) {
			table.writeToDisk(schemaOos, tuplesOos);
		}
		schemaOos.close();
		tuplesOos.close();
	}
	
	public static void readTablesFromDisk()
			throws IOException, ClassNotFoundException, FileNotFoundException {
		
		tablesMap.clear();
		
		ObjectInputStream schemaOis = new ObjectInputStream(new FileInputStream(schemaFileName));
		ObjectInputStream tuplesOis = new ObjectInputStream(new FileInputStream(tuplesFileName));
		
		try {
			int numTables = ((Integer)schemaOis.readObject()).intValue();
			for (int i=0; i<numTables; ++i) {
				Table t = new Table(schemaOis, tuplesOis);
				tablesMap.put(t.getName(), t);
			}
			// check referential constraints of all tables
			for (Table t : tablesMap.values())
				t.verifyForeignKeyConstraints();
			
		} catch (Exception e) {
			tablesMap.clear();
			throw e;
		}
		schemaOis.close();
		tuplesOis.close();
	}
	
	
	public static Table getTable(String tableName) {
		return tablesMap.get(tableName);
	}
	
	public static void verifyExist(String tableName) throws DatabaseException {
		if (!tablesMap.containsKey(tableName)) {
			throw new DatabaseException("Table '"+tableName+"' does not exist.");
		}
	}
	
	public static void verifyNotExist(String tableName) throws DatabaseException {
		if (tablesMap.containsKey(tableName)) {
			throw new DatabaseException("Table '"+tableName+"' already exists.");
		}
	}
	
	public static boolean existsTable(String tableName) {
		return tablesMap.containsKey(tableName);
	}
	
	public static Collection<Table> getTables() {
		return tablesMap.values();
	}
	
	public static Table putTable(Table table) throws DatabaseException {
		return tablesMap.put(table.getName(), table);
	}
}
