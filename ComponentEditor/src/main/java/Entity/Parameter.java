package Entity;

import java.util.List;

public class Parameter {
	
	private String name;
	private int prio;
	private List<ParamType> param;
	
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
	public List<ParamType> getParam() {
		return param;
	}
	public void setParam(List<ParamType> param) {
		this.param = param;
	}
	
	public Parameter(String name, int prio, List<ParamType> param) {
		super();
		this.name = name;
		this.prio = prio;
		this.param = param;
	}
	
	public Parameter() {
		
	}
}
