package Entity;

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
	
	public Dependency(String pre, String post) {
		super();
		this.pre = pre;
		this.post = post;
	}
	
	public Dependency() {
		
	}
}
