package Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Kitten  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3567436498306816067L;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonCreator
	public Kitten(@JsonProperty("name") String name) {
		this.name = name;
	}
	
	public Kitten() {
		
	}
}
