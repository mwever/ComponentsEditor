package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Parameter {
	private String name;
	private String paramTypeName;
	private DefaultDomain defaultDomain;
	private SelectionType[] types;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getParamTypeName() {
		return paramTypeName;
	}


	public void setParamTypeName(String paramTypeName) {
		this.paramTypeName = paramTypeName;
	}


	public DefaultDomain getDefaultDomain() {
		return defaultDomain;
	}
	

	public SelectionType[] getTypes() {
		return types;
	}


	public void setTypes(SelectionType[] types) {
		this.types = types;
	}


	public void setDefaultDomain(DefaultDomain defaultDomain) {
		this.defaultDomain = defaultDomain;
	}

	@JsonCreator
	public Parameter(@JsonProperty("name") String name, @JsonProperty("paramTypeName") String paramTypeName, @JsonProperty("defaultDomain") DefaultDomain defaultDomain, @JsonProperty("types") SelectionType[] types) {
		this.name = name;
		this.paramTypeName = paramTypeName;
		this.defaultDomain = defaultDomain;
		this.setTypes(types);
	}
}
