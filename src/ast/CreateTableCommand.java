package ast;

import java.util.*;
import parser.Token;

public class CreateTableCommand extends Command {
	String table;
	Map<String, AttributeDecl> attributes;	// maps attr name to its own description
	List<String> primaryKey;
	Map<String, AttributeDecl> foreignKeys;	// maps attr name to description of some attr in (prbaboly) another table
	
	public CreateTableCommand(Token tok, String table, List<AttributeDecl> attributeDecls, 
			List<String> primaryKey, Map<String, AttributeDecl> foreignKeys) {
		super(tok);
		this.table = table;
		
		attributes = new HashMap<String, AttributeDecl>();
		for (AttributeDecl a : attributeDecls) {
			attributes.put(a.getName(), a);
		}
		
		this.primaryKey = primaryKey;
		this.foreignKeys = foreignKeys;
	}

	public String getTable() {
		return table;
	}

	public Map<String, AttributeDecl> getAttributes() {
		return attributes;
	}

	public List<String> getPrimaryKey() {
		return primaryKey;
	}

	public Map<String, AttributeDecl> getForeignKeys() {
		return foreignKeys;
	}
	
	
}
