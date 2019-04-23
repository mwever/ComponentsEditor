package Data.intermediate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY )
public class Dependency {
	
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
	public Dependency(@JsonProperty("pre") String pre, @JsonProperty("post") String post){
		this.pre = pre;
		this.post = post;
	}
}
