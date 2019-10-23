package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
@JsonTypeName("bool")
public class BooleanParameterDomain implements DefaultDomain {
	
	private String type = "bool";
	@JsonIgnore
	private Kitten [] values = { new Kitten("true"), new Kitten("false")};
	private String defaultValue ="";
	
	//private String defaultValue;

	/*
	 * public String[] getValues() { return values; }
	 * 
	 * public void setValues(String[] values) { this.values = values; }
	 */

	
	/*
	 * public String getDefaultValue() { return defaultValue; }
	 * 
	 * public void setDefaultValue(String defaultValue) { this.defaultValue =
	 * defaultValue; }
	 */
	
	
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	/*
	 * public String getType() { return type; }
	 * 
	 * public void setType(String type) { this.type = type; }
	 */

	//@JsonProperty("defaultValue") String defaultValue
	//@JsonProperty("values")String [] values, @JsonProperty("type") String type
	
	
	
	@JsonCreator
	public BooleanParameterDomain() {
		super();
		//this.defaultValue = defaultValue;
	}
	
	

}
