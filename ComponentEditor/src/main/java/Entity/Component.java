package Entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Component implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 508345428627420760L;
	private String name;
	private List<ReqInterface> reqInterface;
	private List<ProvidedInterface> providedInterface;
	private List<Parameter> param;
	private List<Dependency> dependency;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReqInterface> getReqInterface() {
		return reqInterface;
	}

	public void setReqInterface(List<ReqInterface> reqInterface) {
		this.reqInterface = reqInterface;
	}

	public List<ProvidedInterface> getProvInterface() {
		return providedInterface;
	}

	public void setProvInterface(List<ProvidedInterface> provInterface) {
		this.providedInterface = provInterface;
	}

	public List<Parameter> getParam() {
		return param;
	}

	public void setParam(List<Parameter> param) {
		this.param = param;
	}

	public List<Dependency> getDependency() {
		return dependency;
	}

	public void setDependency(List<Dependency> dependency) {
		this.dependency = dependency;
	}

	@JsonCreator
	public Component(@JsonProperty("name") String name, @JsonProperty("reqInterface") List<ReqInterface> reqInterface,  @JsonProperty("provInterface") List<ProvidedInterface> provInterface,
			@JsonProperty("param") List<Parameter> param, @JsonProperty("dependency") List<Dependency> dependency) {
		this.name = name;
		this.reqInterface = reqInterface;
		this.providedInterface = provInterface;
		this.param = param;
		this.dependency = dependency;
	}
	
	public Component() {
		
	}
	
	
}
