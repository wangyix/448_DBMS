package ast;

import astvisitor.ASTVisitor;
import exception.DatabaseException;
import parser.Token;

public class BinaryExp extends Exp {
    Exp left, right;
    Token op;

    public BinaryExp(Exp left, Token op, Exp right) {
        super(op);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    //@Override public ASTNode[] children() { return new ASTNode[]{left, right}; }
    public Exp getLeft() { return left; }
    public Exp getRight() { return right; }
    public Token getOp() { return op; }
    
    public void setLeft(Exp left) { this.left = left; }

    public Object accept(ASTVisitor visitor) throws DatabaseException { return visitor.visit(this); }
}
