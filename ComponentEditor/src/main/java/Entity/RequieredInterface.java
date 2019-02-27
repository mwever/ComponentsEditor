package Entity;

public class RequieredInterface {
	private int id;
	private String name;
	private int prio;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrio() {
		return prio;
	}
	public void setPrio(int prio) {
		this.prio = prio;
	}
	
	public RequieredInterface(int id, String name, int prio) {
		super();
		this.id = id;
		this.name = name;
		this.prio = prio;
	}
	
	public RequieredInterface() {
		
	}
}
