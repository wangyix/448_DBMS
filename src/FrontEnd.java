
import database.*;
import exception.DatabaseException;
import parser.*;
import ast.*;
import astvisitor.CommandExecutor;

import java.io.*;
import java.util.*;

public class FrontEnd {
	public static void main(String[] args) {
		
		// read database and users from disk
		try {
			System.out.println("\nReading tables from disk...");
			Database.readTablesFromDisk();
			System.out.println("Successfully read tables from disk.");
		} catch (Exception e) {
			System.out.println("Failed to read tables from disk!");
			System.out.println(e.getMessage()+"\n\nStarting with blank database...");
		}
		try {
			System.out.println("\nReading users from disk...");
			Users.readUsersFromDisk();
			System.out.println("Successfully read users from disk.");
		} catch (Exception e) {
			System.out.println("Failed to read users from disk!");
			System.out.println(e.getMessage()+"\n\nStarting with 'admin' as only user...");
		}
		
		
		// prompt for username if not provided as an arg
		String userName;
		if (args.length < 1) {
			System.out.print("\nUsername: ");
			userName = (new Scanner(System.in)).nextLine();
		} else {
			userName = args[0];
		}
		
		// login user
		try {
			Users.setCurrentUser(userName);
		} catch (DatabaseException e) {
			System.out.println("\n"+e.getMessage());
			return;
		}
		
		
		// parse and execute commands until QUIT
		CommandExtractor extractor = new CommandExtractor(System.in);
		SQLParser parser = new SQLParser(System.in);
		String commandString;
		InputStream commandStream;
		Command command;
		
		boolean quit = false;
		do {
			
			System.out.print("\nSQL> ");
			
			// extract command
			try {
				commandString = extractor.readCommand();
				if (commandString==null) {
					break;
				}
			} catch (IOException | ParseException e) {
				System.out.println("\n"+e.getMessage());
				continue;
			}

			commandStream = new ByteArrayInputStream(
					commandString.getBytes());
			
			// parse command
			try {
				parser.ReInit(commandStream);
				command = parser.Command();
			}
			catch (ParseException | TokenMgrError e) {
				System.out.println("\n"+e.getMessage());
				continue;
			}
			
			// execute command
			try {
				quit = CommandExecutor.execute(command);
			} catch (DatabaseException e) {
				System.out.println("\nDatabase error:\n"+e.getMessage());
				continue;
			}
			
		} while (!quit);
		
		extractor.close();
		
		
		// write database and users to disk
		try {
			Database.writeTablesToDisk();
			System.out.println("\nWrite tables to disk succeeded.");
		} catch (Exception e) {
			System.out.println("\nWrite tables to disk failed!");
			System.out.println(e.getMessage());
		}
		try {
			Users.writeUsersToDisk();
			System.out.println("\nWrite users to disk succeeded.");
		} catch (Exception e) {
			System.out.println("\nWrite users to disk failed!");
			System.out.println(e.getMessage());
		}
	}
}
