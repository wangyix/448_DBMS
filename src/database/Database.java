package database;

import java.util.*;
import exception.DatabaseException;


public class Database {
	
	private static Map<String, Table> tablesMap
			= new HashMap<String, Table>();			// global
	
	
	public static Table getTable(String tableName) {
		return tablesMap.get(tableName);
	}
	
	public static Collection<Table> getTables() {
		return tablesMap.values();
	}
	
	public static Table putTable(Table table) throws DatabaseException {
		return tablesMap.put(table.getName(), table);
	}
	
	public static Table removeTable(String tableName)  throws DatabaseException {
		return tablesMap.remove(tableName);
	}
}
