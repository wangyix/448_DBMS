package ast;

import parser.Token;

public class AttributeExp extends Exp {
    String name;

    public AttributeExp(Token tok, String name) {
        super(tok);
        this.name = name;
    }

    public String getName() { return name; }

    public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
