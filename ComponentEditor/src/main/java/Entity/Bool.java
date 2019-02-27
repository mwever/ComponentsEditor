package Entity;

public class Bool implements ParamType {
	private boolean defaultValue;

	public boolean isDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Bool(boolean defaultValue) {
		super();
		this.defaultValue = defaultValue;
	}
	 public Bool() {
		 
	 }
}
