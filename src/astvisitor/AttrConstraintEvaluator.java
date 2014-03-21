package astvisitor;

import ast.*;
import database.*;
import exception.DatabaseException;


public class AttrConstraintEvaluator extends Evaluator {

	private static Object value;			// may be casted version of val
	private static Attribute attribute;
	
	private static AttrConstraintEvaluator visitor;			// singleton
	
	
	public static Object verifyValueComplies(Object val, Attribute attr)
			throws DatabaseException {
		
		value = val;
		attribute = attr;
		
		String attrName = attribute.getName();
		
		// type check tuple value against its attribute
		
		Attribute.Type expectedType = attribute.getType();
		boolean typeCompatible = false;
		switch (expectedType) {
		case INT:
			if (value instanceof Integer)
				typeCompatible = true;
			break;
		case DECIMAL:
			if (value instanceof Double)
				typeCompatible = true;
			else if (value instanceof Integer) {
				typeCompatible = true;
				value = ((Integer)value).doubleValue();	// if value is int where double is expected, cast it
			}
			break;
		case CHAR:
			if (value instanceof String) {
				String valueStr = (String)value;
				if (valueStr.length() > attribute.getLength()) {
					throw new DatabaseException("String is longer than expected by attribute '"
							+attrName+"'.");
				}
				typeCompatible = true;
			}
		}
		if (!typeCompatible) {
			throw new DatabaseException("Value does not match type expected by attribute '"
					+attrName+"'.");
		}
		
		
		// verify attribute constraint is met
		
		Exp constraint = attribute.getConstraint();
		if (constraint != null) {
		
			if (visitor==null)
				visitor = new AttrConstraintEvaluator();
		
			// no need to type-check result; AttrConstraintTypeChecker
			// should've done that already.
			if (!(boolean)constraint.accept(visitor)) {
				throw new DatabaseException("Value does not meet domain constraint of attribute '"+
						attrName+"'.");
			}
		}
		
		return value;
	}
	
	
	// ------------------------------------------------------------------------
		
	public AttrConstraintEvaluator() {
	}
	
	@Override
	public Object visit(AttributeExp node) throws DatabaseException {
		// Assume that the attribute being referenced is the attribute which
		// has this constraint.  This should've been checked already by
		// AttrConstraintTypeChecker.
		// So, simply return the value in the tuple corresponding to this attribute
		return value;
	}
}