package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import Data.DataCollectionComponentFile;
import Data.intermediate.IntermediateComponent;
import Data.intermediate.Repository;
import Service.RepositoryService;
import hasco.model.Component;
import jaicore.basic.FileUtil;

@RestController
@ComponentScan(basePackageClasses = RepositoryService.class)

public class RepositoryController {

	private static final Logger logger = LoggerFactory.getLogger(RepositoryController.class);

	@Autowired
	private RepositoryService reproService;

	@RequestMapping(value = "/api/repo", method = RequestMethod.GET)
	public Collection<Repository> getAllRepositories() {
		return this.reproService.getAllRepository();
	}

	@RequestMapping(value = "/api/repo/{repoName}", method = RequestMethod.GET)
	public Repository getRepositoryByName(@PathVariable("repoName") final String name) {
		return this.reproService.getRepositoryByName(name);
	}

	@RequestMapping(value = "/api/repo/{repoName}", method = RequestMethod.DELETE)
	public void deleteRepositorybyName(@PathVariable("repoName") final String name) {
		this.reproService.removeRepositoryName(name);
	}

	@PutMapping(value = "/api/repo")
	public void updateRepository(@RequestBody BufferRepo buffer) throws IOException {
		System.out.println("str: " + buffer);

		DataCollectionComponentFile comps = new DataCollectionComponentFile();
		for (IntermediateComponent components : buffer.comps) {
			Component component = ComponentsController.parseComponent(components);
			comps.insertComponent(component);
		}

		Repository repo = new Repository(buffer.name, comps);
		this.reproService.insertRepository(repo);

		System.out.println("PUT");

	}

	@RequestMapping(value = "/api/repo/save/{repoCollectionName}", method = RequestMethod.POST)
	public void saveRepo(@PathVariable("repoCollectionName") final String nameOfRepoCollection) {
		File saveDir = new File("SaveRepo");
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		File saveRepos = new File("SaveRepo/" + nameOfRepoCollection);

		if (!saveRepos.exists()) {
			// RepoCollection did not exsit --> no file exsits

			saveRepos.mkdirs();

			ArrayList<String> zipFiles = new ArrayList<String>();

			for (Repository repo : reproService.getAllRepository()) {
				File saveRepo = new File("SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");
				try {
					if (saveRepo.createNewFile()) {
						for (Component comp : repo.getData().getAllComponents()) {
							ObjectMapper mapper = new ObjectMapper();
							mapper.writeValue(saveRepo, comp);

						}
						zipFiles.add("SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");

					} else {
						throw new IOException("File does allready Exsit");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				FileUtil.zipFiles(zipFiles, "SaveRepo/Download.zip");

				for (Repository repo : reproService.getAllRepository()) {
					File saveRepo = new File("SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");
					
					if (saveRepo.delete()) {
						System.out.println("Delete Worked");
					} else {
						System.out.println("Delet did not work");
					}
				}
				
				File folder = new File("SaveRepo/" + nameOfRepoCollection);
				if(folder.delete()) {
					System.out.println("Delete of Folder worked");
				}
				else {
					System.out.println("Delete of Folder did not worked");
				}

			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		} else {
			// TODO update Files
		}
	}

	@RequestMapping(value = "/api/repo", method = RequestMethod.POST)
	public void insertComponent(@RequestBody BufferRepo buffer) throws IOException {

		DataCollectionComponentFile comps = new DataCollectionComponentFile();
		for (IntermediateComponent components : buffer.comps) {
			Component component = ComponentsController.parseComponent(components);
			comps.insertComponent(component);
		}

		Repository repo = new Repository(buffer.name, comps);
		this.reproService.updateRepository(repo);

		// logger.debug("components"+repo.getData().getAllComponents().toString());
		// System.out.println("str: " + repo.getData().getAllComponents());

	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	static class BufferRepo {
		private String name;
		private IntermediateComponent[] comps;

		public String getName() {
			return this.name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public IntermediateComponent[] getComps() {
			return this.comps;
		}

		public void setComps(final IntermediateComponent[] comps) {
			this.comps = comps;
		}

		@JsonCreator
		BufferRepo(@JsonProperty("name") final String name,
				@JsonProperty("components") final IntermediateComponent[] comps) {
			this.name = name;
			this.comps = comps;
		}
	}
}
