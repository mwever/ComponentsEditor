package Data.intermediate;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
@JsonTypeName("bool")
public class BooleanParameterDomain implements DefaultDomain {
	
	private String type = "bool";
	private ArrayList<Kitten> values = new ArrayList<Kitten>() { 
        { 
            add(new Kitten("true")); 
            add(new Kitten("false")); 
        } 
    }; 
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
		//this.values = values;
		//this.defaultValue = defaultValue;
	}

}
