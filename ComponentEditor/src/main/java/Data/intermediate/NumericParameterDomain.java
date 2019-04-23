package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
@JsonTypeName("number")
public class NumericParameterDomain implements DefaultDomain {
	private double min;
	private double max;
	private boolean isInteger;
	private String type;
	private double defaultValue;
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
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


	public boolean isInteger() {
		return isInteger;
	}


	public void setInteger(boolean isInteger) {
		this.isInteger = isInteger;
	}
	
	public double getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}


	@JsonCreator
	public NumericParameterDomain(@JsonProperty("min") double min, @JsonProperty("max") double max, @JsonProperty("isInteger") boolean isInteger,  @JsonProperty("type") String type,  @JsonProperty("defaultValue") double defaultValue ) {
		super();
		this.min = min;
		this.max = max;
		this.isInteger = isInteger;
		this.type = type;
		this.defaultValue = defaultValue;
	}
	
}
