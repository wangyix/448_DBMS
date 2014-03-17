package value_old;

public class BooleanValue extends Value{

	private static final long serialVersionUID = -5335956364549894831L;

	
	public boolean value;
	
	public BooleanValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		if (value)
			return "TRUE";
		return "FALSE";
	}
}
