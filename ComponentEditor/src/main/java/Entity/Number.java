package Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Number implements ParamType, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -773566885424022919L;
	private double min;
	private double max;
	private double defaultVal;
	private String name; 
	
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
		return defaultVal;
	}
	public void setDefaultValue(double defaultValue) {
		this.defaultVal = defaultValue;
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * public void setName(String name) { this.name = name; }
	 */
	@JsonCreator
	public Number(@JsonProperty("min") double min,@JsonProperty("max") double max,@JsonProperty("defaultVal") double defaultVal) {
		
		this.name = "number";
		this.min = min;
		this.max = max;
		this.defaultVal = defaultVal;
	}
	
	public Number() {
		
	}
	
}
