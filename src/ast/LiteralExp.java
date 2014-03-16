package ast;

import parser.Token;
import value.Value;

public class LiteralExp extends Exp {
	
	public Value value;
	
	public LiteralExp(Token tok, Value value) {
		super(tok);
		this.value = value;
	}
}
