package ast;

import java.util.*;

import parser.Token;

public class InsertCommand extends Command {
	private String table;
	private List<Exp> values;
	
	public InsertCommand(Token tok, String table, List<Exp> values) {
		super(tok);
		this.table = table;
		this.values = values;
	}
	
	public String getTable() { return table; }
	public List<Exp> getValues() { return values; }
	
	public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
