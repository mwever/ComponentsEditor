package Utils;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import hasco.model.Component;

@Service
public class ComponentsSerializer {
	
	public String componentCollectionToJSONRepository(Collection<Component> componentsToSerialize, String repositoryName) {
		
		
		return repositoryName;
	}
	
	public Collection<Component> JSONRepositoryTocomponentCollection(String json) {
		return null;
	}
	
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	class Buffer{
		
	}
}
