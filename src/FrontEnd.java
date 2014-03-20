
import database.*;
import exception.DatabaseException;
import parser.*;
import ast.*;
import astvisitor.CommandExecutor;

public class FrontEnd {
	public static void main(String[] args) {
		
		
		try {
			Database.readTablesFromDisk();
			System.out.println("\nSuccessfully read tables from disk.\n");
		} catch (Exception e) {
			System.out.println("\nFailed to read tables from disk!");
			System.out.println(e.getMessage()+"\n\nStarting with blank database.\n");
		}
		
		SQLParser parser = new SQLParser(System.in);
		Command command;
		
		while(true) {
			
			System.out.print("SQL> ");
			
			// parse command
			try {
				command = parser.Command();
				System.out.println("");
			}
			catch (ParseException e) {
				parser.ReInit(System.in);
				System.out.println("\n"+e.getMessage()+"\n");
				continue;
			}
			catch (TokenMgrError e) {
				parser.ReInit(System.in);
				System.out.println("\n"+e.getMessage()+"\n");
				continue;
			}
			
			// execute command
			try {
				CommandExecutor.execute(command);
				System.out.println("");
			} catch (DatabaseException e) {
				System.out.println("Database error:\n"+e.getMessage()+"\n");
				continue;
			}
			/*
			// TEST: print all tables
			for (Table t : Database.getTables()) {
				t.print();
				System.out.println("");
			}*/
		}
	}
}
