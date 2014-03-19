package astvisitor;

import java.util.*;

import ast.*;
import database.*;
import exception.DatabaseException;
import astvisitor.ASTVisitor.SimpleASTVisitor;

public class CommandExecutor extends SimpleASTVisitor{
	
	private static CommandExecutor visitor;	// singleton
	
	public CommandExecutor() {
	}
	
	public static void execute(Command command) throws DatabaseException {
		
		if (visitor == null)
			visitor = new CommandExecutor();
		
		command.accept(visitor);
	}
	
	
	// CREATE TABLE---------------------------------------------------------
	@Override
	public Object visit(CreateCommand command) throws DatabaseException
	{
		String newTableName = command.getTableName();
		
		// check if table already exists
		if (Database.getTable(newTableName) != null) {
			throw new DatabaseException("Table '"+newTableName
					+"' already exists.");
		}
		Schema newSchema = new Schema(command);
		Table newTable = new Table(newTableName, newSchema);
		Database.putTable(newTable);
		
		System.out.println("Created new table '"+newTableName+"'.");
		return newTable;
	}
	
	
	// DROP TABLE -------------------------------------------------------------
	@Override
	public Object visit(DropCommand command) throws DatabaseException
	{
		String tableName = command.getTableName();
		// check if table exists
		if (Database.getTable(tableName) == null) {
			throw new DatabaseException("Table '"+tableName+"' does not exist.");
		}
		Database.removeTable(tableName);
		
		System.out.println("Dropped table '"+tableName+"'.");
		return null;
	}
	
	
	// SELECT FROM WHERE ------------------------------------------------------
	@Override
	public Object visit(SelectCommand command) throws DatabaseException
	{
		Exp condition = command.getcondition();
		
		Table[] tables;
		Schema[] schemas;			// ith entry is schema of table i
		Tuple[] currentTupleCombo;	// ith entry is the tuple from table i
		
		Attribute[] attributes;
		int[] attrTableIndex;	// ith entry is ith attribute's parent table's index in tables[]
		
		
		// find each table referenced in the WHERE clause, store its schema
		List<String> tableNames = command.getTableNames();
		tables = new Table[tableNames.size()];
		schemas = new Schema[tables.length];
		for (int i=0; i<tableNames.size(); ++i) {
			String tableName = tableNames.get(i);
			tables[i] = Database.getTable(tableName);
			if (tables[i] == null) {
				throw new DatabaseException("Table '"+tableName+"' does not exist.");
			}
			if (tables[i].getTuples().isEmpty()) {
				throw new DatabaseException("Table '"+tableName+"' is empty.");
			}
			schemas[i] = tables[i].getSchema();
		}
		
		// find and record the table and column-position of each selected attribute
		List<String> attrNames = command.getAttrNames();
		
		if (attrNames == null) {	// add all attributes from all tables if SELECT *
			int totalNumAttributes = 0;
			for (Schema s : schemas) 
				totalNumAttributes += s.getAttributes().length;
			
			attributes = new Attribute[totalNumAttributes];
			attrTableIndex = new int[attributes.length];
			
			int attributeIndex = 0;
			for (int tableIndex=0; tableIndex<tables.length; ++tableIndex) {
				Attribute[] schemaAttributes = schemas[tableIndex].getAttributes();
				for (int i=0; i<schemaAttributes.length; ++i) {
					attributes[attributeIndex] = schemaAttributes[i];
					attrTableIndex[attributeIndex] = tableIndex;
					attributeIndex++;
				}
			}
			
		} else {				// otherwise, add attributes specified in the SELECT
			attributes = new Attribute[attrNames.size()];
			attrTableIndex = new int[attributes.length];		
			for (int i=0; i<attributes.length; ++i) {
				
				String attrName = attrNames.get(i);
				boolean attrFound = false;
				
				for (int tableIndex=0; tableIndex<tables.length; ++tableIndex) {
					attributes[i] = schemas[tableIndex].getAttribute(attrName);
					if (attributes[i] != null) {
						attrTableIndex[i] = tableIndex;
						attrFound = true;
						break;
					}
				}
				if (!attrFound) {
					throw new DatabaseException("Selected attribute '"+attrName+"' not found "+
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
			currentTupleCombo[i] = tables[i].getNextTuple();
		}
		// iterate thru all combinations of rows
		int tableIndexToIncrement;
		do {
			
			// check current combination of rows
			Object result = ExpEvaluator.evaluate(condition, currentTupleCombo, schemas);
			if (!(result instanceof Boolean)) {
				throw new DatabaseException("Condition does not evaluate to a boolean.");
			}
			if ((boolean)result) {
				Object[] newValues = new Object[attributes.length];
				for (int j=0; j<newValues.length; ++j) {
					newValues[j] = currentTupleCombo[attrTableIndex[j]]		// tuple
							.getValueAt(attributes[j].getPosition());		// value
				}
				view.append(new Tuple(newValues));
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
		
		view.print();
		return null;
	}
	
	
	// INSERT INTO ------------------------------------------------------------
	@Override
	public Object visit(InsertCommand command)throws DatabaseException
	{
		String tableName = command.getTableName();
		Table table = Database.getTable(tableName);
		if (table == null) {
			throw new DatabaseException("Table '"+tableName+"' does not exist.");
		}
		
		// create a new tuple and evaluate each tuple value expression
		//Tuple newTuple = new Tuple();
		List<Exp> newValueExps = command.getValues();
		Object[] newValues = new Object[newValueExps.size()];
		for (int i=0; i<newValues.length; ++i) {
			newValues[i] = ExpEvaluator.evaluate(newValueExps.get(i), null, null);
		}

		table.addTuple(new Tuple(newValues));
		System.out.println("Added tuple to '"+tableName+"'.");
		return null;
	}
	
	
	// DELETE FROM WHERE ------------------------------------------------------
	@Override
	public Object visit(DeleteCommand command) throws DatabaseException
	{
		return defaultVisit(command);
	}
	
	
	// UPDATE SET WHERE -------------------------------------------------------
	@Override
	public Object visit(UpdateCommand command) throws DatabaseException
	{ return defaultVisit(command); }
	
	// HELP TABLES ------------------------------------------------------------
	@Override
	public Object visit(HelpTablesCommand command)throws DatabaseException
	{ return defaultVisit(command); }
	
	// HELP DESCRIBE ----------------------------------------------------------
	@Override
	public Object visit(HelpCommandCommand command)throws DatabaseException
	{ return defaultVisit(command); }
	
	// HELP command -----------------------------------------------------------
	@Override
	public Object visit(HelpDescribeCommand command)throws DatabaseException
	{ return defaultVisit(command); }
	
	// QUIT -------------------------------------------------------------------
	@Override
	public Object visit(QuitCommand command)throws DatabaseException
	{ return defaultVisit(command); }
}
