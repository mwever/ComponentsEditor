package Data.intermediate;

import Data.DataCollectionComponentFile;

public class Repository {
	private String name;
	private DataCollectionComponentFile data;
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public DataCollectionComponentFile getData() {
		return data;
	}



	public void setData(DataCollectionComponentFile data) {
		this.data = data;
	}



	public Repository(String name, DataCollectionComponentFile data) {
		super();
		this.name = name;
		this.data = data;
	}
}
