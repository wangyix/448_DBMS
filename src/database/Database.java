package database;

import java.util.*;
import exception.DatabaseException;


public class Database {
	
	private static Map<String, Table> tablesMap;	// global
	
	private Database() {
	}
	
	public static Table getTable(String name) {
		return tablesMap.get(name);
	}
	
	public static Table putTable(Table table) throws DatabaseException {
		return tablesMap.put(table.getName(), table);
	}
	
	public static Table removeTable(Table table)  throws DatabaseException {
		return tablesMap.remove(table.getName());
	}
}
