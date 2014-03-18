package astvisitor;

import ast.ASTNode;
import ast.AttributeAssign;
import ast.AttributeExp;
import ast.BinaryExp;
import ast.Command;
import ast.CreateTableCommand;
import ast.DeleteCommand;
import ast.DropTableCommand;
import ast.Exp;
import ast.HelpCommandCommand;
import ast.HelpDescribeCommand;
import ast.HelpTablesCommand;
import ast.InsertCommand;
import ast.LiteralExp;
import ast.QuitCommand;
import ast.SelectCommand;
import ast.UnaryExp;
import ast.UpdateCommand;
import exception.DatabaseException;

public abstract class ASTVisitor {

	public abstract Object visit(ASTNode node) throws DatabaseException;
	public abstract Object visit(AttributeAssign node) throws DatabaseException;
	public abstract Object visit(AttributeExp node) throws DatabaseException;
	public abstract Object visit(BinaryExp node) throws DatabaseException;
	public abstract Object visit(Command node) throws DatabaseException;
	public abstract Object visit(CreateTableCommand node) throws DatabaseException;
	public abstract Object visit(DeleteCommand node) throws DatabaseException;
	public abstract Object visit(DropTableCommand node) throws DatabaseException;
	public abstract Object visit(Exp node) throws DatabaseException;
	public abstract Object visit(HelpCommandCommand node) throws DatabaseException;
	public abstract Object visit(HelpDescribeCommand node) throws DatabaseException;
	public abstract Object visit(HelpTablesCommand node) throws DatabaseException;
	public abstract Object visit(InsertCommand node) throws DatabaseException;
	public abstract Object visit(LiteralExp node) throws DatabaseException;
	public abstract Object visit(QuitCommand node) throws DatabaseException;
	public abstract Object visit(SelectCommand node) throws DatabaseException;
	public abstract Object visit(UnaryExp node) throws DatabaseException;
	public abstract Object visit(UpdateCommand node)throws DatabaseException;

	
	public static class SimpleASTVisitor extends ASTVisitor {
		public Object defaultVisit(ASTNode node) throws DatabaseException {
			throw new DatabaseException("Unsupported node type");
		}
		@Override public Object visit(ASTNode node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(AttributeAssign node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(AttributeExp node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(BinaryExp node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(Command node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(CreateTableCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(DeleteCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(DropTableCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(Exp node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(HelpCommandCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(HelpDescribeCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(HelpTablesCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(InsertCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(LiteralExp node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(QuitCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(SelectCommand node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(UnaryExp node) throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(UpdateCommand node) throws DatabaseException { return defaultVisit(node); }
	}
}
