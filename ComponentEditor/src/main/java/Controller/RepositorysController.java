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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import Data.intermediate.Repository;
import Service.RepositoryService;
import hasco.model.Component;

@RestController
@ComponentScan(basePackageClasses = RepositoryService.class)
@RequestMapping("/repos")

public class RepositorysController {

	@Autowired
	private RepositoryService reproService;
	
	@RequestMapping(method =RequestMethod.GET)
	public Collection<Repository> getAllRepositories(){
		return this.reproService.getAllRepository();
	}
	
	@RequestMapping(value = "/{reproName}", method = RequestMethod.GET)
	public Repository getRepositoryByName(@PathVariable("reproName") String name) {
		return this.reproService.getRepositoryByName(name);
	}
	
	@RequestMapping(value = "/{reproName}", method = RequestMethod.DELETE)
	public void deleteRepositorybyName(@PathVariable("reproName") String name) {
		this.reproService.removeRepositoryName(name);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateRepository(@RequestBody String str) throws IOException {
		System.out.println("str: "+ str);
		ObjectMapper map = new ObjectMapper();
		BufferRepo buffer = map.readValue(str, BufferRepo.class); 
		Component [] comps = new Component[buffer.comps.length];
		int count = 0;
		for(String components : buffer.comps) {
			Component component = ComponentsController.parseComponent(components);
			comps[count] = component;
			count++;
		}
		
		//Repository repo = new Repository(buffer.name, comps);
		
		//reproService.insertRepository(repo);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public void insertComponent(@RequestBody String str) throws IOException {
		System.out.println("str: "+ str);	
		//reproService.updateRepository(repro);
	}
	
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
	class BufferRepo {
		private String name;
		private String [] comps;
		
		
		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String[] getComps() {
			return comps;
		}


		public void setComps(String[] comps) {
			this.comps = comps;
		}


		@JsonCreator
		BufferRepo(@JsonProperty("name") String name,@JsonProperty("comps") String [] comps){
			this.name = name;
			this.comps= comps;
		}
	}
}
