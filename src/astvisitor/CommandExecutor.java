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
		List<String> tableNames = command.getTableNames();
		
		// find each table referenced in the WHERE clause
		Table[] tables = new Table[tableNames.size()];
		for (int i=0; i<tableNames.size(); ++i) {
			String tableName = tableNames.get(i);
			tables[i] = Database.getTable(tableName);
			if (tables[i] == null) {
				throw new DatabaseException("Table '"+tableName+"' does not exist.");
			}
		}
		
		
		
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
			newValues[i] = ExpEvaluator.evaluate(newValueExps.get(i), null);
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
