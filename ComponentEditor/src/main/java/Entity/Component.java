package Entity;

import java.util.List;

public class Component {
	private String name;
	private List<RequieredInterface> reqInterface;
	private List<ProvidedInterface> provInterface;
	private List<Parameter> param;
	private List<Dependency> dependency;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RequieredInterface> getReqInterface() {
		return reqInterface;
	}

	public void setReqInterface(List<RequieredInterface> reqInterface) {
		this.reqInterface = reqInterface;
	}

	public List<ProvidedInterface> getProvInterface() {
		return provInterface;
	}

	public void setProvInterface(List<ProvidedInterface> provInterface) {
		this.provInterface = provInterface;
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

	
	public Component(String name, List<RequieredInterface> reqInterface, List<ProvidedInterface> provInterface,
			List<Parameter> param, List<Dependency> dependency) {
		super();
		this.name = name;
		this.reqInterface = reqInterface;
		this.provInterface = provInterface;
		this.param = param;
		this.dependency = dependency;
	}
	
	public Component() {
		
	}
	
	
}
