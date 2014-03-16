package value;

public class IntValue extends Value {

	private static final long serialVersionUID = 4938631206644886969L;
	
	public int value;
	
	public IntValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
