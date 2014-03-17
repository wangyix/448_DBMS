package ast;

import exception.DatabaseException;
import parser.Token;

public class ASTNode {
    Token tok;

    public ASTNode(Token tok) {
        this.tok = tok;
    }

    /*
    public ASTNode[] children() {
        return new ASTNode[0];
    }*/

    public Token getToken() { return tok; }

    public Object accept(ASTVisitor visitor) throws DatabaseException {
        return visitor.visit(this);
    }
}
