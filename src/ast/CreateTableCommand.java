package ast;

import java.util.*;

import parser.Token;
import schema.Attribute;

public class CreateTableCommand extends Command {
	String table;
	// maps attr name to its own description
	Map<String, Attribute> attributes;
	List<String> primaryKey;
	// maps attr name to description of some attr in foreign table
	Map<String, Attribute> foreignKeys;
	
	public CreateTableCommand(Token tok, String table, List<Attribute> Attributes, 
			List<String> primaryKey, Map<String, Attribute> foreignKeys) {
		super(tok);
		this.table = table;
		
		attributes = new HashMap<String, Attribute>();
		for (Attribute a : Attributes) {
			attributes.put(a.getName(), a);
		}
		
		this.primaryKey = primaryKey;
		this.foreignKeys = foreignKeys;
	}

	public String getTable() {
		return table;
	}

	public Map<String, Attribute> getAttributes() {
		return attributes;
	}

	public List<String> getPrimaryKey() {
		return primaryKey;
	}

	public Map<String, Attribute> getForeignKeys() {
		return foreignKeys;
	}
	
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
