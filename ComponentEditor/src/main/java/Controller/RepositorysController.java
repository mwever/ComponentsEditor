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

import Data.intermediate.Repository;
import Service.RepositoryService;

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
		//reproService.insertRepository(repro);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public void insertComponent(@RequestBody String str) throws IOException {
		System.out.println("str: "+ str);	
		//reproService.updateRepository(repro);
	}
}
