package ast;

import parser.Token;

public class StringLiteralExp extends Exp {
    String value;

    public StringLiteralExp(Token tok, String value) {
        super(tok);
        this.value = value;
    }

    public String getValue() { return value; }

    public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}