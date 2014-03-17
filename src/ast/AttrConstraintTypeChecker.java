package ast;

import database.*;
import exception.DatabaseException;
import ast.ASTVisitor.SimpleASTVisitor;

public class AttrConstraintTypeChecker extends SimpleASTVisitor{
	
	private static Attribute attribute;
	private static AttrConstraintTypeChecker visitor;	// singleton
	
	public static void check(Schema sch, Attribute attr) throws DatabaseException {
		attribute = attr;
		
		if (visitor == null)
			visitor = new AttrConstraintTypeChecker();
		
		ExpType constraintType = (ExpType)attribute.getConstraint().accept(visitor);
		if (constraintType != ExpType.EXP_BOOLEAN) {
			throw new DatabaseException("Constraint for attribute '"
					+attribute.getName()+"' does not evaluate to a boolean value.");
		}
	}
	
	// ------------------------------------------------------------------------
	
	private enum ExpType {EXP_INT, EXP_DOUBLE, EXP_STRING, EXP_BOOLEAN}
	
	public AttrConstraintTypeChecker() {
	}
	
	
	@Override
	public Object visit(AttributeExp node) throws DatabaseException {
		
		if (!node.getName().equals(attribute.getName())) {
			throw new DatabaseException("Constraint for attribute '"
					+attribute.getName()+"' cannot reference other attributes.");
		}
		ExpType ret = null;
		switch (attribute.getType()){
		case INT:
			ret = ExpType.EXP_INT;
		case DECIMAL:
			ret = ExpType.EXP_DOUBLE;
		case CHAR:
			ret = ExpType.EXP_STRING;
		}
		return ret;
	}
	
	
	@Override
	public Object visit(BinaryExp node) throws DatabaseException {
		
		ExpType ret = null;
		
		ExpType left = (ExpType)node.getLeft().accept(this);
		String op = node.getOp().image;
		ExpType right = (ExpType)node.getRight().accept(this);
		
		boolean leftIsInt = (left==ExpType.EXP_INT);
		boolean rightIsInt = (right==ExpType.EXP_INT);
		boolean leftIsNumber = leftIsInt || (left==ExpType.EXP_DOUBLE);
		boolean rightIsNumber = rightIsInt || (left==ExpType.EXP_DOUBLE);
		
		if (leftIsInt && rightIsInt) {
			if (op.equals("AND") || op.equals("OR")) {
				throw new DatabaseException("Op '"+op+"' cannot be applied between ints.");
            } else if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
				ret = ExpType.EXP_INT;
			} else {
				ret = ExpType.EXP_BOOLEAN;
			}
		}
		else if (leftIsNumber && rightIsNumber) {
			if (op.equals("AND") || op.equals("OR")) {
				throw new DatabaseException("Op '"+op+"' cannot be applied between decimals.");
			} else if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
				ret = ExpType.EXP_DOUBLE;
			} else {
				ret = ExpType.EXP_BOOLEAN;
			}
		}
		else if (left==ExpType.EXP_STRING && right==ExpType.EXP_STRING) {
			if (op.equals("<") || op.equals(">") || op.equals(">")
					 || op.equals(">") || op.equals(">") || op.equals(">")) {
				ret = ExpType.EXP_BOOLEAN;
            } else {
            	throw new DatabaseException("Op '"+op+"' cannot be applied between strings.");
            }
		}
		else if (left==ExpType.EXP_BOOLEAN && right==ExpType.EXP_BOOLEAN) {
			if (op.equals("AND") || op.equals("||")) {
				ret = ExpType.EXP_BOOLEAN;
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
		ExpType ret = null;
		
		Object value = node.getValue();
		if (value instanceof Integer)
			ret = ExpType.EXP_INT;
		else if (value instanceof Double)
			ret = ExpType.EXP_DOUBLE;
		else
			ret = ExpType.EXP_STRING;
		
		return ret;
	}
	
	
	@Override
	public Object visit(UnaryExp node) throws DatabaseException {
			
		ExpType sub = (ExpType)node.getSub().accept(this);
		if (sub!=ExpType.EXP_INT && sub!=ExpType.EXP_DOUBLE) {
			String op = node.getOp().image;
			throw new DatabaseException("Unary operator '"+op
					+"' can only be applied to type int or decimal.");
		}
		return sub;
	}
}
