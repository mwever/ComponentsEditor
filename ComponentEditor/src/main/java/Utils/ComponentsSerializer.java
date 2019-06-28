package Utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hasco.model.Component;
import hasco.serialization.ComponentLoader;

@Service
public class ComponentsSerializer {

	public static String componentCollectionToJSONRepository(final Collection<Component> componentsToSerialize, final String repositoryName) throws JsonProcessingException {

		JsonNodeFactory factory = new JsonNodeFactory(true);

		ObjectNode root = factory.objectNode();
		root.set("repository", factory.textNode(repositoryName));

		ArrayNode componentsArray = factory.arrayNode();

		for (Component c : componentsToSerialize) {
			ObjectNode comp = factory.objectNode();
			comp.set("name", factory.textNode(c.getName()));
			componentsArray.add(comp);
		}
		root.set("components", componentsArray);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(root);
	}

	public Collection<Component> JSONRepositoryTocomponentCollection(final String json) {
		return null;
	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	class Buffer {

	}

	public static void main(final String[] args) throws IOException {
		ComponentLoader cl = new ComponentLoader(new File("../../AILibs/softwareconfiguration/mlplan/resources/automl/searchmodels/meka/weka-singlelabel-base.json"));
		String repoString = componentCollectionToJSONRepository(cl.getComponents(), "My Repository");
		System.out.println(repoString);
	}

}
