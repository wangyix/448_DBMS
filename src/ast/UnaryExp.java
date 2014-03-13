package ast;

import parser.Token;

public class UnaryExp extends Exp {
	String op;
	Exp sub;
    
    public UnaryExp(Token tok, String op, Exp sub) {
        super(tok);
        this.op = op;
        this.sub = sub;
    }

    @Override public ASTNode[] children() { return new ASTNode[]{sub}; }
    public Exp getSub() { return sub; }

    public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
