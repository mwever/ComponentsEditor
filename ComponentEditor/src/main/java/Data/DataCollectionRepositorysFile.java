package Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
@Qualifier("FileDataRepro")

public class DataCollectionRepositorysFile implements DataCollectionRepository{
	private Map<String,Data.intermediate.Repository> repsitorys = new HashMap<>();

	public Map<String,Data.intermediate.Repository> getRepsitorys() {
		return repsitorys;
	}

	public void setRepsitorys(Map<String,Data.intermediate.Repository> repsitorys) {
		this.repsitorys = repsitorys;
	}
	
	
	@Override
	public Collection<Data.intermediate.Repository> getAllRepository() {

		return this.repsitorys.values();
	}

	@Override
	public Data.intermediate.Repository getRepositoryByName(String name) {
		return this.repsitorys.get(name);
	}

	@Override
	public void removeRepositoryByName(String name) {
		this.repsitorys.remove(name);
	}

	@Override
	public void updateRepository(Data.intermediate.Repository repo) {
		this. repsitorys.replace(repo.getName(),repo);
		
	}

	@Override
	public void insertRepository(Data.intermediate.Repository repo) {
		this.repsitorys.put(repo.getName(), repo);
	}

}
