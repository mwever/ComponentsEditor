package Data.intermediate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
//import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
/*
 * @JsonSubTypes({
 * 
 * @JsonSubTypes.Type(value = BooleanParameterDomain.class, name = "boolean") })
 */
  @JsonTypeName("cat")

public class CategoricalParameterDomain implements DefaultDomain {
	private String type = "cat";
	private List<Kitten> values;
	private String defaultValue;
	
	/*
	 * public String getType() { return type; }
	 * 
	 * 
	 * public void setType(String type) { this.type = type; }
	 */

	public List<Kitten> getValues() {
		return values;
	}


	public void setValues(List<Kitten> values) {
		this.values = values;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	// @JsonProperty("type") String type,
	@JsonCreator
	public CategoricalParameterDomain(@JsonProperty("values") List<Kitten> values, @JsonProperty("defaultValue") String defaultValue) {
		this.values = values;
		//this.type = type;
		this.defaultValue = defaultValue;
	} 
}
