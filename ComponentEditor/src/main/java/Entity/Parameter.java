package Entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Parameter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7823822356385091837L;
	private String name;
	private int prio;
	private ParamType paramtype;
	
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
	public ParamType getParam() {
		return paramtype;
	}
	public void setParam(ParamType param) {
		this.paramtype = param;
	}
	
	@JsonCreator
	public Parameter(@JsonProperty("name") String name,@JsonProperty("prio") int prio, @JsonProperty("param") ParamType param) {
		this.name = name;
		this.prio = prio;
		this.paramtype = param;
	}
	
	public Parameter() {
		
	}
}
