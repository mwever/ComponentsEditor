package Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqInterface implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int prio;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrio() {
		return prio;
	}
	public void setPrio(int prio) {
		this.prio = prio;
	}
	
	@JsonCreator
	public ReqInterface(@JsonProperty("id")int id,@JsonProperty("name") String name,@JsonProperty("prio") int prio) {
		
		this.id = id;
		this.name = name;
		this.prio = prio;
	}
	
	public ReqInterface() {
		
	}
}
