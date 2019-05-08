package Service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import Data.DataCollectionRepository;
import Data.intermediate.Repository;

@Service
@ComponentScan(basePackageClasses = DataCollectionRepository.class)
@Qualifier("FileDataRepro")
public class RepositoryService {
	@Autowired
	private DataCollectionRepository dataCollectionRepro;
	
	public Collection<Repository> getAllRepository(){
		return this.dataCollectionRepro.getAllRepository();
	}
	
	public Repository getRepositoryByName(String name) {
		return dataCollectionRepro.getRepositoryByName(name);
		/*
		 * if(componentData.getAllComponents().contains(name)) { return
		 * componentData.getComponentByName(name); } else {
		 * System.out.println("The Component does not exsit"); return null; }
		 */
	}

	public void removeRepositoryName(String name) {
		this.dataCollectionRepro.removeRepositoryByName(name);
	}
	
	public void updateRepository(Repository repro) {
		dataCollectionRepro.updateRepository(repro);
	}

	public void insertRepository(Repository repro) {
		dataCollectionRepro.insertRepository(repro);
	}
}
