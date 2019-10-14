package Utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;

import hasco.model.Component;
import jaicore.basic.sets.SetUtil.Pair;

@Service
public class ComponentsDeserializer {
	public static Pair<String,Collection<Component>> JSONRepositorytoComponentCollection(String json) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
	    //JsonNode actualObj = mapper.readTree(json);
	    
	   
	    JsonFactory factory = mapper.getFactory();
	    JsonParser parser = factory.createParser(json);
	    JsonNode root = mapper.readTree(parser);
	    
	    root.get("repository");
	 
	    assertNotNull(root);
	    
	    // When
		/*
		 * JsonNode jsonNode1 = actualObj.get("k1"); assertThat(jsonNode1.textValue(),
		 * equals("v1"));
		 */
		return null;
		
	}
}
