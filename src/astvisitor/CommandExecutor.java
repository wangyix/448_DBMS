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
	public Object visit(CreateCommand command) throws DatabaseException
	{
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
	public Object visit(DropCommand command) throws DatabaseException
	{
		String tableName = command.getTableName();
		Database.dropTable(tableName);
		
		System.out.println("Dropped table '"+tableName+"'.");
		return null;
	}
	
	
	// SELECT FROM WHERE ------------------------------------------------------
	@Override
	public Object visit(SelectCommand command) throws DatabaseException
	{
		List<String> attrNamesList = command.getAttrNames();
		List<String> tableNamesList = command.getTableNames();
		
		String[] attrNames = null;
		if (attrNamesList != null) {
			attrNamesList.toArray(new String[attrNamesList.size()]);
		}
		String[] tableNames = tableNamesList.toArray(new String[tableNamesList.size()]);
		
		View view = Database.createView(attrNames, tableNames, command.getcondition());
		
		view.print();
		return null;
	}
	
	
	// INSERT INTO ------------------------------------------------------------
	@Override
	public Object visit(InsertCommand command)throws DatabaseException
	{
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
	public Object visit(DeleteCommand command) throws DatabaseException
	{
		String tableName = command.getTableName();
		Database.verifyExist(tableName);
		Table table = Database.getTable(tableName);
		
		table.deleteTuples(command.getCondition());
		return null;
	}
	
	
	// UPDATE SET WHERE -------------------------------------------------------
	@Override
	public Object visit(UpdateCommand command) throws DatabaseException
	{
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
