package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class UnaryExp extends Exp {
	Token op;
	Exp sub;
    
    public UnaryExp(Token tok, Token op, Exp sub) {
        super(tok);
        this.op = op;
        this.sub = sub;
    }

    //@Override public ASTNode[] children() { return new ASTNode[]{sub}; }
    public Exp getSub() { return sub; }
    public Token getOp() { return op; }
    
    public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
