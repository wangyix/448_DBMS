package ast;

import parser.Token;

public class Exp extends ASTNode {
    public Exp(Token tok) { super(tok); }
    public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
