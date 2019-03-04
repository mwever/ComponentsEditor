package Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BoolVal implements ParamType,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8985411591822292354L;
	private boolean defaultVal;
	private String name = "bool"; 

	
	public String getName() {
		return name;
	}

	public boolean getDefaultValue() {
		return defaultVal;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultVal = defaultValue;
	}
	
	@JsonCreator
	public BoolVal(@JsonProperty("defaultValue") boolean defaultValue) {
		this.defaultVal = defaultValue;
	}
	 public BoolVal() {
		 
	 }
}
