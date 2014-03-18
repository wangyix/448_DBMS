package astvisitor;

import java.util.*;

import database.*;
import exception.DatabaseException;
import ast.AttributeExp;
import ast.BinaryExp;
import ast.Exp;
import ast.LiteralExp;
import ast.UnaryExp;
import astvisitor.ASTVisitor.SimpleASTVisitor;

public class ExpEvaluator extends SimpleASTVisitor {
	
	private static List<Tuple> tuples;			// no two tuples from same table, use null for literals only
	private static ExpEvaluator visitor;		// singleton
	
	
	public static Object evaluate(Exp exp, List<Tuple> referencedTuples)
			throws DatabaseException {
		
		if (visitor==null)
			visitor = new ExpEvaluator();
		
		tuples = referencedTuples;
		return exp.accept(visitor);
	}
	
	// ------------------------------------------------------------------------
		
	public ExpEvaluator() {
	}
	
	@Override
	public Object visit(AttributeExp node) throws DatabaseException {
		
		Object ret = null;
		
		// tuples can be null if the expression can only reference literals
		if (tuples == null) {
			throw new DatabaseException("Attributes cannot be referenced when specifying a value.");
		}
		
		// find this attribute's position and containing tuple
		String attrName = node.getName();
		boolean attrFound = false;
		for (Tuple t : tuples) {
			Integer position = t.getParentTable().getSchema()
							.getAttributePosition(attrName);
			if (position != null) {
				attrFound = true;
				ret = t.getValueAt(position);
				break;
			}
		}
		if (!attrFound) {
			throw new DatabaseException("Attribute '"+attrName+
					"' referenced in condition does not exist.");
		}
		return ret;
	}
	
	@Override
	public Object visit(BinaryExp node) throws DatabaseException {
		
		Object ret = null;
		
		Object left = node.getLeft().accept(visitor);
		String op = node.getOp().image;
		Object right = node.getRight().accept(visitor);
		
		boolean leftIsInt = left instanceof Integer;
		boolean rightIsInt = right instanceof Integer;
		boolean leftIsNumber = leftIsInt || left instanceof Double;
		boolean rightIsNumber = rightIsInt || right instanceof Double;
		
		if (leftIsInt && rightIsInt) {
			int l = (int)left;
			int r = (int)right;
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
			double l = (double)left;
			double r = (double)right;
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
			boolean l = (boolean)left;
			boolean r = (boolean)right;
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
