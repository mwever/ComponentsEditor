package Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Data.DataCollectionComponentFile;
import Data.intermediate.BooleanParameterDomain;
import Data.intermediate.CategoricalParameterDomain;
import Data.intermediate.IntermediateComponent;
import Data.intermediate.Kitten;
import Data.intermediate.NumericParameterDomain;
import Data.intermediate.Parameter;
import Data.intermediate.ProvidedInterface;
import Data.intermediate.Repository;
import Data.intermediate.RequiredInterface;
import Data.intermediate.SelectionType;
import Service.RepositoryService;
import Utils.ComponentsSerializer;
import hasco.model.Component;
import hasco.serialization.ComponentLoader;
import jaicore.basic.FileUtil;

@RestController
@ComponentScan(basePackageClasses = RepositoryService.class)
@ComponentScan(basePackageClasses = ComponentsSerializer.class)

public class RepositoryController {

	private static final Logger logger = LoggerFactory.getLogger(RepositoryController.class);

	@Autowired
	private RepositoryService reproService;

	@Autowired
	private ComponentsSerializer serializer;

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

						Gson gson = new GsonBuilder().setPrettyPrinting().create();
						JsonParser jp = new JsonParser();
						FileWriter writer = new FileWriter(
								"SaveRepo/" + nameOfRepoCollection + "/" + repo.getName() + ".json");
						JsonElement je = jp.parse(ComponentsSerializer.componentCollectionToJSONRepository(
								repo.getData().getAllComponents(), repo.getName()));
						gson.toJson(je, writer);
						writer.close();

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
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonParser jp = new JsonParser();
				FileWriter writer = new FileWriter("SaveRepo/SaveSingleRepo/" + nameOfRepoToDownload + ".json");
				JsonElement je = jp.parse(ComponentsSerializer.componentCollectionToJSONRepository(
						repoToDownload.getData().getAllComponents(), repoToDownload.getName()));
				gson.toJson(je, writer);
				writer.close();

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
	 //ResponseEntity<String>
	public void uploadRepo(@RequestParam("file") MultipartFile file) throws IOException {
		byte[] input;
		File saveDir = new File("LoadRepo");
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		if (!file.isEmpty()) {
			input = file.getBytes();
			ZipInputStream zipStream = new ZipInputStream(new ByteArrayInputStream(input));
			ZipEntry entry = null;
			while ((entry = zipStream.getNextEntry()) != null) {

				String entryName = entry.getName();
				System.out.println(entryName);

				FileOutputStream out = new FileOutputStream("LoadRepo/" + entryName);

				byte[] byteBuff = new byte[4096];
				int bytesRead = 0;
				while ((bytesRead = zipStream.read(byteBuff)) != -1) {
					out.write(byteBuff, 0, bytesRead);
				}

				out.close();
				zipStream.closeEntry();
			}
			zipStream.close();

			readInRepoFile();
			
			deleteContentOfDirectory( new File("LoadRepo"));

		}
		 //return new ResponseEntity<>("worked", HttpStatus.OK);
		 
		// InputStream inJson = (InputStream)
		// Repository.class.getResourceAsStream("LoadRepo/test.json");
		// Repository test = new ObjectMapper().readValue(inJson, Repository.class);

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

	/*
	 * private IntermediateComponent reverseComponentparse(Component comp) {
	 * IntermediateComponent output = new IntermediateComponent(comp.getName());
	 * 
	 * ArrayList<ProvidedInterface> pInterface = new ArrayList<>(); for(String
	 * providedInterface : comp.getProvidedInterfaces()) { ProvidedInterface prov =
	 * new ProvidedInterface(providedInterface); pInterface.add(prov); }
	 * output.setProvidedInterfaces(pInterface);
	 * 
	 * ArrayList<RequiredInterface> rInterface = new ArrayList<>(); for(String id :
	 * comp.getRequiredInterfaces().keySet()) { RequiredInterface req = new
	 * RequiredInterface(id, comp.getRequiredInterfaces().get(id));
	 * rInterface.add(req); } output.setRequiredInterfaces(rInterface);
	 * 
	 * 
	 * ArrayList<Parameter> params = new ArrayList<>(); SelectionType cat = new
	 * SelectionType("Cat", new CategoricalParameterDomain(new ArrayList<Kitten>(),
	 * "cat", "")); SelectionType num = new SelectionType("Number", new
	 * NumericParameterDomain(0,0, false, "number",0)); String[] values =
	 * {"true","false"}; SelectionType bool = new SelectionType("Bool", new
	 * BooleanParameterDomain(values, "bool", "")); for(hasco.model.Parameter param
	 * : comp.getParameters()) { if(param.isCategorical()) {
	 * if(param.getDefaultDomain() instanceof hasco.model.BooleanParameterDomain) {
	 * 
	 * } }else if(param.isNumeric()) {
	 * 
	 * } //Parameter p = new Parameter(param.getName(), paramTypeName,
	 * defaultDomain, types)
	 * 
	 * } return null; }
	 */

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

	private void deleteContentOfDirectory(File toDelet) {

		File[] files = toDelet.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				f.delete();
			}
		}

	}

	private Repository readInRepoFile() throws IOException {
		File dir = new File("LoadRepo");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				ComponentLoader components = new ComponentLoader(child);
				DataCollectionComponentFile comps = new DataCollectionComponentFile();
				for (Component comp : components.getComponents()) {
					comps.insertComponent(comp);
				}

				Repository repo = new Repository(child.getName(), comps);
				this.reproService.insertRepository(repo);
			}
		} else {
			throw new IOException("The to uploaded file was not created");
		}
		return null;

	}
}
