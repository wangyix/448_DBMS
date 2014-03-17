package value_old;

public class DecimalValue extends Value {

	private static final long serialVersionUID = 8038673609526186935L;

	public double value;
	
	public DecimalValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
}
