package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
@JsonTypeName("bool")
public class BooleanParameterDomain implements DefaultDomain {
	
	private String[] values;
	private String type;
	private String defaultValue;

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@JsonCreator
	public BooleanParameterDomain(@JsonProperty("values")String [] values, @JsonProperty("type") String type,  @JsonProperty("defaultValue") String defaultValue) {
		this.values = values;
		this.defaultValue = defaultValue;
	}
}
