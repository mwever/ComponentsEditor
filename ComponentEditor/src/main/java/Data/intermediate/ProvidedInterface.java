package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class ProvidedInterface {
	
	private String interfaces;
	
	
	public String getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(String interfaces) {
		this.interfaces = interfaces;
	}

	@JsonCreator
	public ProvidedInterface(@JsonProperty("interfaces") String interfaces) {
		this.interfaces = interfaces;
	}
}
