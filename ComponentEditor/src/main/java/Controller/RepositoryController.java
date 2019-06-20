package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
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
import com.fasterxml.jackson.databind.ObjectWriter;
//import com.google.common.io.Files;
import com.fasterxml.jackson.databind.SerializationFeature;

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

	@RequestMapping(value = "/api/repo/save/{repoCollectionName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void saveRepos(HttpServletResponse response,
			@PathVariable("repoCollectionName") final String nameOfRepoCollection) {
		File saveDir = new File("SaveRepo");
		if (!saveDir.exists()) {
			saveDir.mkdir();
		} else {
			logger.info("SaveRpeo was allready created ");
		}
		File saveRepos = new File("SaveRepo/" + nameOfRepoCollection);

		if (!saveRepos.exists()) {
			// RepoCollection did not exsit --> no file exsits

			saveRepos.mkdirs();

			ArrayList<String> zipFiles = new ArrayList<>();

			for (Repository repo : reproService.getAllRepository()) {
				File saveRepo = new File("SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");
				try {
					if (saveRepo.createNewFile()) {
						
						ObjectMapper mapper = new ObjectMapper();
						mapper.enable(SerializationFeature.INDENT_OUTPUT);
						mapper.writeValue(saveRepo, repo.getData().getAllComponents());
						zipFiles.add("SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");

					} else {
						throw new IOException("File does allready Exsit");
					}
				} catch (IOException e) {
					logger.error("The File that you wanted to create allready exsits and can therefor not be created "
							+ saveRepo.getName());
				}
			}

			try {
				FileUtil.zipFiles(zipFiles, "SaveRepo/Download.zip");

				for (Repository repo : reproService.getAllRepository()) {
					File saveRepo = new File("SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");

					if (saveRepo.delete()) {
						logger.info("Delete Worked");
					} else {
						logger.info("Delet did not work");
					}
				}

				File folder = new File("SaveRepo/" + nameOfRepoCollection);
				if (folder.delete()) {
					logger.info("Delete of Folder worked");
				} else {
					logger.info("Delete of Folder did not worked");
				}

				response.setContentType("application/zip");

				File zipFile = new File("SaveRepo/Download.zip");
				byte[] zipFileasByte = java.nio.file.Files.readAllBytes(zipFile.toPath());

				response.getOutputStream().write(zipFileasByte);
				response.getOutputStream().close();
				response.flushBuffer();

				zipFile.delete();

			} catch (FileNotFoundException e) {
				logger.error("One of the following files does not exsist " + zipFiles.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// TODO update Files

		}
	}

	@RequestMapping(value = "api/repo/save/single/{nameOfRepoToDownload}", method = RequestMethod.POST)
	public void saveSingleRepo(HttpServletResponse response,
			@PathVariable("nameOfRepoToDownload") final String nameOfRepoToDownload) {

		File saveDir = new File("SaveRepo");
		if (!saveDir.exists()) {
			saveDir.mkdir();
		} else {
			logger.info("SaveRepo was allready created ");
		}

		File saveDirSingle = new File("SaveRepo/SaveSingleRepo");
		if (!saveDirSingle.exists()) {
			saveDirSingle.mkdir();
		} else {
			logger.info("SaveSingleRepo was allready created ");
		}

		File saveRepo = new File("SaveRepo/SaveSingleRepo/" + nameOfRepoToDownload + ".json");

		if (!saveRepo.exists()) {
			// RepoCollection did not exsit --> no file exsits

			Repository repoToDownload = reproService.getRepositoryByName(nameOfRepoToDownload);

			try {
				 ObjectMapper mapper = new ObjectMapper();
				 mapper.enable(SerializationFeature.INDENT_OUTPUT);
				mapper.writeValue(saveRepo, repoToDownload.getData().getAllComponents());
				
			} catch (IOException e) {
				logger.error("The File that you wanted to create allready exsits and can therefor not be created "
						+ saveRepo.getName());
			}
		}

		response.setContentType("application/json");

		File downloadRepo = new File("SaveRepo/SaveSingleRepo/" + nameOfRepoToDownload + ".json");
		byte[] downloadRepoByte;
		try {
			downloadRepoByte = java.nio.file.Files.readAllBytes(downloadRepo.toPath());
			response.getOutputStream().write(downloadRepoByte);
			response.getOutputStream().close();
			response.flushBuffer();

			downloadRepo.delete();
		} catch (IOException e) {
			logger.error("Read Bytes, write Bytes, get Output stream, flush Buffer or delet did not work");
		}

	}
	
	@RequestMapping(value = "/api/repo/upload/zip", method = RequestMethod.POST)
	public void uploadRepo(HttpServletResponse response) {
		System.out.println("Hat was gemacht");
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
