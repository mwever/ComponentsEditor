package Controller;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import Entity.BoolVal;
import Entity.Cat;
import Entity.Component;
import Service.ComponentService;




@RestController
@ComponentScan(basePackageClasses = ComponentService.class)
@RequestMapping("/components")
public class ComponentsController{
	@Autowired
	private ComponentService componentService;
	
		  
	
	
	@RequestMapping(method =RequestMethod.GET)
	public Collection<Component> getAllComponents(){
		return this.componentService.getAllComponents();
	}
	
	@RequestMapping(value = "/{compName}", method = RequestMethod.GET)
	public Component getComponentByName(@PathVariable("compName") String name) {
		return this.componentService.getComponentByName(name);
	}
	
	@RequestMapping(value = "/{compName}", method = RequestMethod.DELETE)
	public void deleteComponentbyName(@PathVariable("compName") String name) {
		this.componentService.removeComponentByName(name);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateComponent(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("TEST");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.registerSubtypes(new NamedType(Number.class, "Number"));
		objectMapper.registerSubtypes(new NamedType(BoolVal.class, "Boolval"));
		objectMapper.registerSubtypes(new NamedType(Cat.class, "Cat"));
		
		Component component = objectMapper.readValue(json, Component.class);
		this.componentService.updateComponent(component);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void insertComponent(@RequestBody String json) throws JsonParseException, JsonMappingException, IOException {
		System.out.println("TEST");
		ObjectMapper objectMapper = new ObjectMapper();
		Component component = objectMapper.readValue(json, Component.class);
		this.componentService.insertComponent(component);
	}
}
