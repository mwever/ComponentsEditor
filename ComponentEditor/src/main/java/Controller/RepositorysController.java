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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import Data.DataCollectionComponentFile;
import Data.intermediate.Repository;
import Service.RepositoryService;
import hasco.model.Component;

@RestController
@ComponentScan(basePackageClasses = RepositoryService.class)
@RequestMapping("/")

public class RepositorysController {

	@Autowired
	private RepositoryService reproService;

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Repository> getAllRepositories() {
		return this.reproService.getAllRepository();
	}

	@RequestMapping(value = "/{reproName}", method = RequestMethod.GET)
	public Repository getRepositoryByName(@PathVariable("reproName") final String name) {
		return this.reproService.getRepositoryByName(name);
	}

	@RequestMapping(value = "/{reproName}", method = RequestMethod.DELETE)
	public void deleteRepositorybyName(@PathVariable("reproName") final String name) {
		this.reproService.removeRepositoryName(name);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateRepository(@RequestBody final String str) throws IOException {
		System.out.println("str: " + str);
		ObjectMapper map = new ObjectMapper();
		map.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		BufferRepo buffer = map.readValue(str, BufferRepo.class);
		DataCollectionComponentFile comps = new DataCollectionComponentFile();
		for (String components : buffer.comps) {
			Component component = ComponentsController.parseComponent(components);
			comps.insertComponent(component);
		}

		Repository repo = new Repository(buffer.name, comps);
		this.reproService.insertRepository(repo);

	}

	@RequestMapping(method = RequestMethod.POST)
	public void insertComponent(@RequestBody final String str) throws IOException {
		ObjectMapper map = new ObjectMapper();
		map.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		BufferRepo buffer = map.readValue(str, BufferRepo.class);
		DataCollectionComponentFile comps = new DataCollectionComponentFile();
		for (String components : buffer.comps) {
			Component component = ComponentsController.parseComponent(components);
			comps.insertComponent(component);
		}

		Repository repo = new Repository(buffer.name, comps);
		this.reproService.updateRepository(repo);

		System.out.println("str: " + str);
		// reproService.updateRepository(repro);
	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	static class BufferRepo {
		private String name;
		private String[] comps;

		public String getName() {
			return this.name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public String[] getComps() {
			return this.comps;
		}

		public void setComps(final String[] comps) {
			this.comps = comps;
		}

		@JsonCreator
		BufferRepo(@JsonProperty("name") final String name, @JsonProperty("components") final String[] comps) {
			this.name = name;
			this.comps = comps;
		}
	}
}
