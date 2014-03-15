package ast;

import parser.Token;

public class AttributeAssign extends ASTNode {
    AttributeExp target;
	Exp value;

    public AttributeAssign(Token tok, AttributeExp target, Exp value) {
        super(tok);
        this.target = target;
        this.value = value;
    }

    @Override public ASTNode[] children() {
    	if (value==null)
 			return new ASTNode[]{target};
 		else
    		return new ASTNode[]{target, value};
    }
    
    public AttributeExp getTarget() { return target; }
    public Exp getValue() { return value; }

    public Object accept(ASTVisitor visitor) { return visitor.visit(this); }
}
