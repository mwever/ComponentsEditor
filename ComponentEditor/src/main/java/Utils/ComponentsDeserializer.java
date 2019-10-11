package Utils;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import hasco.model.Component;
import jaicore.basic.sets.SetUtil.Pair;

@Service
public class ComponentsDeserializer {
	public static Pair<String,Collection<Component>> JSONRepositorytoComponentCollection(String json) throws JsonProcessingException {
		return null;
		
	}
}
