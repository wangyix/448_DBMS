package database;

public class TupleWithSchema  {
	
	private Tuple tuple;
	private Schema parentSchema;
	
	public TupleWithSchema(Tuple tuple, Schema parentSchema) {
		this.tuple = tuple;
		this.parentSchema = parentSchema;
	}
	
	public Tuple getTuple() {
		return tuple;
	}
	
	public Schema getParentSchema() {
		return parentSchema;
	}
	
	public void print() {
		tuple.print(parentSchema);
	}
}
