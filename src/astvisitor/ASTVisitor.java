package astvisitor;

import ast.*;
import exception.DatabaseException;

public abstract class ASTVisitor {

	public abstract Object visit(ASTNode node) throws DatabaseException;
	public abstract Object visit(AttributeExp node) throws DatabaseException;
	public abstract Object visit(BinaryExp node) throws DatabaseException;
	public abstract Object visit(Command node) throws DatabaseException;
	public abstract Object visit(CreateSubschemaCommand node) throws DatabaseException;
	public abstract Object visit(CreateTableCommand node) throws DatabaseException;
	public abstract Object visit(CreateUserCommand node) throws DatabaseException;
	public abstract Object visit(DeleteCommand node) throws DatabaseException;
	public abstract Object visit(DeleteSubschemaCommand node) throws DatabaseException;
	public abstract Object visit(DeleteUserCommand node) throws DatabaseException;
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
		@Override public Object visit(AttributeExp node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(BinaryExp node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(Command node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(CreateSubschemaCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(CreateTableCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(CreateUserCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(DeleteCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(DeleteSubschemaCommand node)  throws DatabaseException { return defaultVisit(node); }
		@Override public Object visit(DeleteUserCommand node)  throws DatabaseException { return defaultVisit(node); }
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
