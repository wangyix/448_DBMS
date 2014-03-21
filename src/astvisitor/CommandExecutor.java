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
	
	public static boolean execute(Command command) throws DatabaseException {
		
		if (visitor == null)
			visitor = new CommandExecutor();
		
		return (boolean)command.accept(visitor);
	}
	
	
	// admin commands *************************************************************************************************
	
	// CREATE USER ------------------------------------------------------------
	@Override
	public Object visit(CreateUserCommand command) throws DatabaseException {
		Users.verifyCurrentUserRankAtLeast(Users.Type.ADMIN);
		String userName = command.getUserName();
		Users.addUser(userName, command.getUserType());
		System.out.println("\nUser '"+userName+"' created.");
		return false;
	}
	
	// DELETE USER ------------------------------------------------------------
	@Override
	public Object visit(DeleteUserCommand command) throws DatabaseException {
		Users.verifyCurrentUserRankAtLeast(Users.Type.ADMIN);
		String userName = command.getUserName();
		Users.deleteUser(userName);
		System.out.println("\nUser '"+userName+"' deleted.");
		return false;
	}
	
	// CREATE SUBSCHEMA -------------------------------------------------------
	@Override
	public Object visit(CreateSubschemaCommand command) throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.ADMIN);
		
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		boolean noPrevSubschema = table.getSchema()
				.setSubschema(command.getAttrNames());
		if (noPrevSubschema)
			System.out.println("\nSubschema for table '"+tableName+"' created.");
		else
			System.out.println("\nSubschema for table '"+tableName+"' updated.");
		return false;
	}
	
	// DELETE SUBSCHEMA -------------------------------------------------------
	@Override
	public Object visit(DeleteSubschemaCommand command) throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.ADMIN);
		
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		table.getSchema().deleteSubschema();
		System.out.println("\nSubschema for table '"+tableName+"' deleted.");
		return false;
	}
	
	
	
	// SQL commands ***************************************************************************************************
	
	// CREATE TABLE---------------------------------------------------------
	@Override
	public Object visit(CreateTableCommand command) throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.USER_A);
		
		String newTableName = command.getTableName();
		
		// check if table already exists
		Database.verifyNotExist(newTableName);
		
		Schema newSchema = new Schema(command);
		Table newTable = new Table(newTableName, newSchema);
		Database.putTable(newTable);
		
		System.out.println("\nCreated new table '"+newTableName+"'.");
		return false;
	}
	
	
	// DROP TABLE -------------------------------------------------------------
	@Override
	public Object visit(DropTableCommand command) throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.USER_A);
		
		String tableName = command.getTableName();
		Database.dropTable(tableName);
		
		System.out.println("\nDropped table '"+tableName+"'.");
		return false;
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
		
		System.out.println("");
		int rowsPrinted = view.print();
		if (rowsPrinted == 1)
			System.out.println("\n1 row selected.");
		else
			System.out.println("\n"+rowsPrinted+" rows selected.");
		return false;
	}
	
	
	// INSERT INTO ------------------------------------------------------------
	@Override
	public Object visit(InsertCommand command)throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.USER_A);
		
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
		System.out.println("\nAdded tuple to '"+tableName+"'.");
		return false;
	}
	
	
	// DELETE FROM WHERE ------------------------------------------------------
	@Override
	public Object visit(DeleteCommand command) throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.USER_A);
		
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		int numDeleted = table.deleteTuples(command.getCondition());
		if (numDeleted == 1)
			System.out.println("\n1 tuple deleted");
		else
			System.out.println("\n"+numDeleted+" tuples deleted.");
		return false;
	}
	
	
	// UPDATE SET WHERE -------------------------------------------------------
	@Override
	public Object visit(UpdateCommand command) throws DatabaseException {
		
		Users.verifyCurrentUserRankAtLeast(Users.Type.USER_A);
		
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
		
		int numUpdated = table.updateTuples(command.getCondition(),
				updateAttrNames, updateValues);
		if (numUpdated == 1)
			System.out.println("\n1 tuple updated.");
		else
			System.out.println("\n"+numUpdated+" tuples updated.");
		return false;
	}
	
	
	// HELP TABLES ------------------------------------------------------------
	@Override
	public Object visit(HelpTablesCommand command)throws DatabaseException {
		Collection<Table> tables = Database.getTables();
		if (tables.isEmpty()) {
			System.out.println("\nNo tables found.");
			return false;
		}
		System.out.println("");
		for (Table t : tables) {
			System.out.println(t.getName());
		}
		return false;
	}
	
	// HELP DESCRIBE ----------------------------------------------------------
	@Override
	public Object visit(HelpDescribeCommand command)throws DatabaseException {
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		System.out.println("");
		table.getSchema().printDescription();
		return false;
	}
	
	// HELP command -----------------------------------------------------------
	@Override
	public Object visit(HelpCommandCommand command)throws DatabaseException {
		System.out.println("");
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
		return false;
	}
	
	// QUIT -------------------------------------------------------------------
	@Override
	public Object visit(QuitCommand command)throws DatabaseException {
		return true;
	}
}
