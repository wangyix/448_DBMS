package astvisitor;

import exception.DatabaseException;
import ast.*;
import astvisitor.ASTVisitor.SimpleASTVisitor;

public class Evaluator extends SimpleASTVisitor{
	
	
	@Override
	public Object visit(BinaryExp node) throws DatabaseException {
		
		Object ret = null;
		
		Object left = node.getLeft().accept(this);
		String op = node.getOp().image;
		Object right = node.getRight().accept(this);
		
		boolean leftIsInt = left instanceof Integer;
		boolean rightIsInt = right instanceof Integer;
		boolean leftIsNumber = leftIsInt || left instanceof Double;
		boolean rightIsNumber = rightIsInt || right instanceof Double;
		
		if (leftIsInt && rightIsInt) {
			int l = ((Integer)left).intValue();
			int r = ((Integer)right).intValue();
			if (op.equals("<")) {
                ret = (l < r);
            } else if (op.equals("<=")) {
                ret = (l <= r);
            } else if (op.equals("=")) {
                ret = (l == r);
            } else if (op.equals("!=")) {
                ret = (l != r);
            } else if (op.equals(">")) {
                ret = (l > r);
            } else if (op.equals(">=")) {
                ret = (l >= r);
            } else if (op.equals("+")) {
                ret = (l + r);
            } else if (op.equals("-")) {
                ret = (l - r);
            } else if (op.equals("*")) {
                ret = (l * r);
            } else if (op.equals("/")) {
                ret = (l / r);
            } else {
            	throw new DatabaseException("Op '"+op+"' cannot be applied between ints.");
            }
		}
		else if (leftIsNumber && rightIsNumber) {
			double l;
			if (leftIsInt)
				l = ((Integer)left).doubleValue();
			else
				l = ((Double)left).doubleValue();
			double r;
			if (rightIsInt)
				r = ((Integer)right).doubleValue();
			else
				r = ((Double)right).doubleValue();
			if (op.equals("<")) {
                ret = (l < r);
            } else if (op.equals("<=")) {
                ret = (l <= r);
            } else if (op.equals("=")) {
                ret = (l == r);
            } else if (op.equals("!=")) {
                ret = (l != r);
            } else if (op.equals(">")) {
                ret = (l > r);
            } else if (op.equals(">=")) {
                ret = (l >= r);
            } else if (op.equals("+")) {
                ret = (l + r);
            } else if (op.equals("-")) {
                ret = (l - r);
            } else if (op.equals("*")) {
                ret = (l * r);
            } else if (op.equals("/")) {
                ret = (l / r);
            } else {
            	throw new DatabaseException("Op '"+op+"' cannot be applied between decimals.");
            }
		}
		else if (left instanceof String && right instanceof String) {
			String l = (String)left;
			String r = (String)right;
			int diff = l.compareTo(r);	// l-r
			if (op.equals("<")) {
                ret = (diff < 0);
            } else if (op.equals("<=")) {
                ret = (diff <= 0);
            } else if (op.equals("=")) {
                ret = (diff == 0);
            } else if (op.equals("!=")) {
                ret = (diff != 0);
            } else if (op.equals(">")) {
                ret = (diff > 0);
            } else if (op.equals(">=")) {
                ret = (diff >= 0);
            } else {
            	throw new DatabaseException("Op '"+op+"' cannot be applied between strings.");
            }
		}
		else if (left instanceof Boolean && right instanceof Boolean) {
			boolean l = ((Boolean)left).booleanValue();
			boolean r = ((Boolean)right).booleanValue();
			op = op.toUpperCase();	// important!!
			if (op.equals("AND")) {
				ret = l && r;
			} else if (op.equals("OR")) {
				ret = l || r;
			} else {
            	throw new DatabaseException("Op '"+op+"' cannot be applied between conditions.");
            }
		}
		else {
			throw new DatabaseException("Op '"+op+
					"' is being applied between expressions of different types.");
		}
		return ret;
	}
	
	@Override
	public Object visit(LiteralExp node) {
		return node.getValue();
	}
	
	@Override
	public Object visit(UnaryExp node) throws DatabaseException {
		
		Object ret = null;
		
		Object sub = node.getSub().accept(this);
		String op = node.getOp().image;
		if (sub instanceof Double) {
			if (op.equals("-"))
				ret = -((double)sub);
			else
				ret = (double)sub;
		}
		else if (sub instanceof Integer) {
			if (op.equals("-"))
				ret = -((int)sub);
			else
				ret = (int)sub;
		}
		else {
			throw new DatabaseException("Unary operator '"+op
					+"' can only be applied to type int or decimal.");
		}
		return ret;
	}
}
