package astvisitor;

import java.util.*;

import ast.*;
import ast.UpdateCommand.UpdateDescriptor;
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
	public Object visit(CreateCommand command) throws DatabaseException {
		
		String newTableName = command.getTableName();
		
		// check if table already exists
		Database.verifyNotExist(newTableName);
		
		Schema newSchema = new Schema(command);
		Table newTable = new Table(newTableName, newSchema);
		Database.putTable(newTable);
		
		System.out.println("Created new table '"+newTableName+"'.");
		return newTable;
	}
	
	
	// DROP TABLE -------------------------------------------------------------
	@Override
	public Object visit(DropCommand command) throws DatabaseException {
		
		String tableName = command.getTableName();
		Database.dropTable(tableName);
		
		System.out.println("Dropped table '"+tableName+"'.");
		return null;
	}
	
	
	// SELECT FROM WHERE ------------------------------------------------------
	@Override
	public Object visit(SelectCommand command) throws DatabaseException {
		
		List<String> attrNamesList = command.getAttrNames();
		List<String> tableNamesList = command.getTableNames();
		
		String[] attrNames = null;
		if (attrNamesList != null) {
			attrNames = attrNamesList.toArray(new String[attrNamesList.size()]);
		}
		String[] tableNames = tableNamesList.toArray(new String[tableNamesList.size()]);
		
		View view = Database.createView(attrNames, tableNames, command.getcondition());
		
		view.print();
		return null;
	}
	
	
	// INSERT INTO ------------------------------------------------------------
	@Override
	public Object visit(InsertCommand command)throws DatabaseException {
		
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
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
	public Object visit(DeleteCommand command) throws DatabaseException {
		
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		table.deleteTuples(command.getCondition());
		return null;
	}
	
	
	// UPDATE SET WHERE -------------------------------------------------------
	@Override
	public Object visit(UpdateCommand command) throws DatabaseException {
		
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		// get the attribute names to update; evaluate each update value
		List<UpdateDescriptor> updateDescriptors = command.getUpdateDescriptors();
		String[] updateAttrNames = new String[updateDescriptors.size()];
		Object[] updateValues = new Object[updateAttrNames.length];
		for (int i=0; i<updateAttrNames.length; ++i) {
			UpdateDescriptor ud = updateDescriptors.get(i);
			updateAttrNames[i] = ud.getAttrName();
			updateValues[i] = ExpEvaluator.evaluate(ud.getValue(), null, null);
		}
		
		table.updateTuples(command.getCondition(), updateAttrNames, updateValues);
		return null;
	}
	
	
	// HELP TABLES ------------------------------------------------------------
	@Override
	public Object visit(HelpTablesCommand command)throws DatabaseException {
		Collection<Table> tables = Database.getTables();
		if (tables.isEmpty()) {
			System.out.println("No tables found.");
			return null;
		}
		for (Table t : tables) {
			System.out.println(t.getName());
		}
		return null;
	}
	
	// HELP DESCRIBE ----------------------------------------------------------
	@Override
	public Object visit(HelpDescribeCommand command)throws DatabaseException {
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		table.getSchema().printDescription();
		return null;
	}
	
	// HELP command -----------------------------------------------------------
	@Override
	public Object visit(HelpCommandCommand command)throws DatabaseException {
		switch (command.getType()) {
		case CREATE_TABLE:
			System.out.println("Creates a new table.\n"
					+"\n"
					+"Format:\n"
					+"\n"
					+"CREATE TABLE <table_name> (\n"
					+"<attr_name> <type>,\n"
					+"<attr_name> <type> (?)CHECK (<condition> AND|OR ...),\n"
					+"...\n"
					+"PRIMARY KEY (<attr_name>, ...),\n"
					+"(?)FOREIGN KEY (<attr_name>, ...) REFERENCES <table_name>(<attr_name>, ...)\n"
					+");\n"
					+"\n"
					+"(?) indicates an optional section.\n");
			break;
		case DROP_TABLE:
			System.out.println("Removes a table from the database.\n"
					+"\n"
					+"Format:\n"
					+"\n"
					+"DROP TABLE <table_name>;\n");
			break;
		case SELECT:
			System.out.println("Prints out a view of the table.\n"
					+"\n"
					+"Format:\n"
					+"\n"
					+"SELECT <attr_name>, ...\n"
					+"FROM <table_name>, ...\n"
					+"(?)WHERE <condition> AND|OR ...;\n"
					+"\n"
					+"(?) indicates an optional section.\n");
			break;
		case INSERT:
			System.out.println("Inserts a new tuple into a table.\n"
					+"\n"
					+"Format:\n"
					+"\n"
					+"INSERT INTO <table_name>\n"
					+"VALUES (<value>, ...);\n");
			break;
		case DELETE:
			System.out.println("Deletes tuples from a table that meet a specified condition.\n"
					+"\n"
					+"Format:\n"
					+"\n"
					+"DELETE FROM <table_name>\n"
					+"(?)WHERE <condition> AND|OR ...;\n"
					+"\n"
					+"(?) indicates an optional section.\n");
			break;
		case UPDATE:
			System.out.println("Updates tuples from a table that meet a specified condition.\n"
					+"\n"
					+"Format:\n"
					+"\n"
					+"UPDATE <table_name>\n"
					+"SET <attr_name>=<value>, ...\n"
					+"(?)WHERE <condition> AND|OR ...;\n"
					+"\n"
					+"(?) indicates an optional section.\n");
			break;
		}
		return null;
	}
	
	// QUIT -------------------------------------------------------------------
	@Override
	public Object visit(QuitCommand command)throws DatabaseException {
		try {
			Database.writeTablesToDisk();
			System.out.println("Write tables to disk succeeded.");
		} catch (Exception e) {
			System.out.println("Write tables to disk failed!");
			System.out.println(e.getMessage());
		} finally {
			System.exit(0);
		}
		return null;
	}
}
