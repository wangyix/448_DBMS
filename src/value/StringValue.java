package value;

public class StringValue extends Value {

	private static final long serialVersionUID = 4040863393125112987L;
	
	public String value;
	
	public StringValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
