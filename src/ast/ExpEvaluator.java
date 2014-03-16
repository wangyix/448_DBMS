package ast;

import java.util.*;

import database.*;
import ast.ASTVisitor.SimpleASTVisitor;

public class ExpEvaluator extends SimpleASTVisitor {

	Tuple tuple;
	List<List<Attribute>> schemas;
	
	public ExpEvaluator() {
		
	}
	/*
	@Override public Object visit(AttributeExp node) {
		sb.append(node.getName());
		return null; 
	}
	
	@Override public Object visit(BinaryExp node) {
		return defaultVisit(node);
	}
	
	@Override public Object visit(IntLiteralExp node) {
		return defaultVisit(node);
	}
	

	@Override public Object visit(StringLiteralExp node) {
		return defaultVisit(node);
	}
	
	@Override public Object visit(UnaryExp node) {
		return defaultVisit(node);
	}
	
	public String getString() {
		return sb.toString();
	}*/

}
