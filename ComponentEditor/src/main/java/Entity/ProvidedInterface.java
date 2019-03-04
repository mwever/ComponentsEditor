package Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProvidedInterface implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8329073443260228548L;
	private String interfaces;

	public String getProvInterface() {
		return interfaces;
	}

	public void setProvInterface(String provInterface) {
		this.interfaces = provInterface;
	}
	
	@JsonCreator
	public ProvidedInterface(@JsonProperty("provInterface") String provInterface) {
		
		this.interfaces = provInterface;
	}
	
	public ProvidedInterface() {
		
	}
}
