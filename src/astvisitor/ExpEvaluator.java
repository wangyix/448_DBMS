package astvisitor;

import database.*;
import exception.DatabaseException;
import ast.*;

public class ExpEvaluator extends Evaluator {
	
	private static Tuple[] tuples;		// no two tuples from same table, use null for literals-only exps
	private static Schema[] schemas;	// corresponding parent schemas of the tuples
	private static ExpEvaluator visitor;			// singleton
	
	
	public static boolean evaluateCondition(Exp condition, Tuple[] refTuples, Schema[] parentSchemas)
			throws DatabaseException {
		if (condition == null)		// if no condition, everything passes
			return true;
		Object result = evaluate(condition, refTuples, parentSchemas);
		if (!(result instanceof Boolean)) {
			throw new DatabaseException("Condition does not evaluate to a boolean.");
		}
		return (boolean)result;
	}
	
	// assume exp not null
	public static Object evaluate(Exp exp, Tuple[] refTuples, Schema[] parentSchemas)
			throws DatabaseException {
		
		if (visitor==null)
			visitor = new ExpEvaluator();
		
		tuples = refTuples;
		schemas = parentSchemas;
		return exp.accept(visitor);
	}
	
	
	// ------------------------------------------------------------------------
		
	public ExpEvaluator() {
	}
	
	@Override
	public Object visit(AttributeExp node) throws DatabaseException {
		
		Object ret = null;
		
		// tuples can be null if the expression can only reference literals (for INSERT)
		if (tuples == null) {
			throw new DatabaseException("Attributes cannot be referenced when specifying a value.");
		}
		
		// find this attribute's position and containing tuple
		String attrName = node.getName();
		boolean attrFound = false;
		for (int i=0; i<tuples.length; ++i) {
			Integer position = schemas[i].getAttributePosition(attrName);
			if (position != null) {
				attrFound = true;
				ret = tuples[i].getValueAt(position);
				break;
			}
		}
		if (!attrFound) {
			throw new DatabaseException("Attribute '"+attrName+
					"' referenced in condition does not exist.");
		}
		return ret;
	}
	
	
}
