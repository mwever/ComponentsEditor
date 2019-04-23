package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class SelectionType {
	
	private String label;
	private DefaultDomain actualType;
	
	
	
	public String getLabel() {
		return label;
	}



	public void setLabel(String label) {
		this.label = label;
	}



	public DefaultDomain getActualType() {
		return actualType;
	}



	public void setActualType(DefaultDomain actualType) {
		this.actualType = actualType;
	}


	@JsonCreator
	public SelectionType(@JsonProperty("label") String label, @JsonProperty("actualType") DefaultDomain actualType) {
		this.label = label;
		this.actualType = actualType;
	}
}
