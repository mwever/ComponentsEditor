package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Kitten {
	private String name;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@JsonCreator
	public Kitten(@JsonProperty("name") String name) {
		super();
		this.name = name;
	}
	
	
	public Kitten() {
		super();
	}
	
}
