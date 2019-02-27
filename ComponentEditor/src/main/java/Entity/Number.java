package Entity;

public class Number implements ParamType {
	private double min;
	private double max;
	private double defaultValue;
	
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public double getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Number(double min, double max, double defaultValue) {
		super();
		this.min = min;
		this.max = max;
		this.defaultValue = defaultValue;
	}
	
	public Number() {
		
	}
}
