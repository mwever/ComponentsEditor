package Data.intermediate;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class IntermediateComponent {
	
	private String name;
	private List<RequiredInterface> requiredInterfaces;
	private List<ProvidedInterface> providedInterfaces;
	private List<Parameter> parameters;
	private List<Dependency> dependencies;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<RequiredInterface> getRequiredInterfaces() {
		return requiredInterfaces;
	}


	public void setRequiredInterfaces(List<RequiredInterface> requiredInterfaces) {
		this.requiredInterfaces = requiredInterfaces;
	}


	public List<ProvidedInterface> getProvidedInterfaces() {
		return providedInterfaces;
	}


	public void setProvidedInterfaces(List<ProvidedInterface> providedInterfaces) {
		this.providedInterfaces = providedInterfaces;
	}


	public List<Parameter> getParameters() {
		return parameters;
	}


	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}


	public List<Dependency> getDependencies() {
		return dependencies;
	}


	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	@JsonCreator
	public IntermediateComponent (@JsonProperty("name") String name,@JsonProperty("requiredInterfaces") List<RequiredInterface> requiredInterfaces, @JsonProperty("providedInterfaces") List<ProvidedInterface> providedInterfaces, @JsonProperty("parameters") List<Parameter> parameters, @JsonProperty("dependencies") List<Dependency> dependencies) {
		this.name = name;
		this.requiredInterfaces = requiredInterfaces;
		this.providedInterfaces = providedInterfaces;
		this.parameters = parameters;
		this.dependencies = dependencies;
	}
}
