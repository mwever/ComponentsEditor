package Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import Entity.Component;
import Entity.Dependency;

@Repository
@Qualifier("FileData")
public class DataCollectionComponentFile implements DataCollectionComponent {
	private static Map<String,Component> components;
	
	static {
		ArrayList<Dependency> test = new ArrayList<Dependency>() {{
			add(new Dependency("wichtig","wichtiger"));
		}};
		
		components = new HashMap<String,Component>(){
			{
				put("test", new Component("test",null,null,null,null));
				put("Robin", new Component("Robin",null,null,null,test));
			}	
		};
	}
	
	/* (non-Javadoc)
	 * @see Data.ComponentData#getAllComponents()
	 */
	@Override
	public Collection<Component> getAllComponents(){
		return this.components.values();
	}
	
	/* (non-Javadoc)
	 * @see Data.ComponentData#getComponentByName(java.lang.String)
	 */
	@Override
	public Component getComponentByName(String name) {
		return this.components.get(name);
	}

	/* (non-Javadoc)
	 * @see Data.ComponentData#removeComponentByName(java.lang.String)
	 */
	@Override
	public void removeComponentByName(String name) {
		this.components.remove(name);
	}
	
	/* (non-Javadoc)
	 * @see Data.ComponentData#updateComponent(Entity.Component)
	 */
	@Override
	public void updateComponent(Component component) {
		Component comp = this.components.get(component.getName());
		comp.setDependency(component.getDependency());
		comp.setParam(component.getParam());
		comp.setProvInterface(component.getProvInterface());
		comp.setReqInterface(component.getReqInterface());
		this.components.put(component.getName(), comp);
	}

	/* (non-Javadoc)
	 * @see Data.ComponentData#insertComponent(Entity.Component)
	 */
	@Override
	public void insertComponent(Component component) {
		this.components.put(component.getName(), component);
	}
	
}
