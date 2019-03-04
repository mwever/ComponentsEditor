package Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dependency implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4593319221057634255L;
	private String pre;
	private String post;
	
	public String getPre() {
		return pre;
	}
	public void setPre(String pre) {
		this.pre = pre;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@JsonCreator
	public Dependency(@JsonProperty("pre") String pre, @JsonProperty("post") String post) {
		this.pre = pre;
		this.post = post;
	}
	
	public Dependency() {
		
	}
}
