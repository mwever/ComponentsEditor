package Entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cat implements ParamType, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4256579033771944858L;
	private List<Kitten> cats;
	private String name;
	private String defaultVal;
	
	public String getName() {
		return name;
	}
	
	public List<Kitten> getCats() {
		return cats;
	}

	public void setCats(List<Kitten> cats) {
		this.cats = cats;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public List<Kitten> getKittens() {
		return cats;
	}

	public void setKittens(List<Kitten> kittens) {
		this.cats = kittens;
	}
	
	@JsonCreator
	public Cat(@JsonProperty("defaultVal") String defaultVal) {
		this.defaultVal = defaultVal;
		this.name = "cat";
	}
	
	public Cat() {
		
	}
}
