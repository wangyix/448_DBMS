package ast;

public abstract class ASTVisitor {

	public abstract Object visit(ASTNode node);
	public abstract Object visit(AttributeAssign node);
	public abstract Object visit(AttributeExp node);
	public abstract Object visit(BinaryExp node);
	public abstract Object visit(Command node);
	public abstract Object visit(CreateTableCommand node);
	public abstract Object visit(DeleteCommand node);
	public abstract Object visit(DropTableCommand node);
	public abstract Object visit(Exp node);
	public abstract Object visit(HelpCommandCommand node);
	public abstract Object visit(HelpDescribeCommand node);
	public abstract Object visit(HelpTablesCommand node);
	public abstract Object visit(InsertCommand node);
	public abstract Object visit(LiteralExp node);
	public abstract Object visit(QuitCommand node);
	public abstract Object visit(SelectCommand node);
	public abstract Object visit(UnaryExp node);
	public abstract Object visit(UpdateCommand node);

	
	public static class SimpleASTVisitor extends ASTVisitor {
		public Object defaultVisit(ASTNode node) {
			throw new Error("Unsupported node type");
		}
		@Override public Object visit(ASTNode node) { return defaultVisit(node); }
		@Override public Object visit(AttributeAssign node) { return defaultVisit(node); }
		@Override public Object visit(AttributeExp node) { return defaultVisit(node); }
		@Override public Object visit(BinaryExp node) { return defaultVisit(node); }
		@Override public Object visit(Command node) { return defaultVisit(node); }
		@Override public Object visit(CreateTableCommand node) { return defaultVisit(node); }
		@Override public Object visit(DeleteCommand node) { return defaultVisit(node); }
		@Override public Object visit(DropTableCommand node) { return defaultVisit(node); }
		@Override public Object visit(Exp node) { return defaultVisit(node); }
		@Override public Object visit(HelpCommandCommand node) { return defaultVisit(node); }
		@Override public Object visit(HelpDescribeCommand node) { return defaultVisit(node); }
		@Override public Object visit(HelpTablesCommand node) { return defaultVisit(node); }
		@Override public Object visit(InsertCommand node) { return defaultVisit(node); }
		@Override public Object visit(LiteralExp node) { return defaultVisit(node); }
		@Override public Object visit(QuitCommand node) { return defaultVisit(node); }
		@Override public Object visit(SelectCommand node) { return defaultVisit(node); }
		@Override public Object visit(UnaryExp node) { return defaultVisit(node); }
		@Override public Object visit(UpdateCommand node) { return defaultVisit(node); }
	}
}
