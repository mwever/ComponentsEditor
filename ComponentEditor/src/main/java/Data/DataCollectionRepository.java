package Data;

import java.util.Collection;

import Data.intermediate.Repository;


public interface DataCollectionRepository {
	Collection<Repository> getAllRepository();

	Repository getRepositoryByName(String name);

	void removeRepositoryByName(String name);

	void updateRepository(Repository repo);

	void insertRepository(Repository repo);
}
