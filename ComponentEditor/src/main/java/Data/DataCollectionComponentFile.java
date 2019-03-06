package Data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import hasco.model.Component;
import hasco.model.Dependency;
import hasco.model.Parameter;



@Repository
@Qualifier("FileData")
public class DataCollectionComponentFile implements DataCollectionComponent {
	private Map<String,Component> components;
	
	
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
		for(Dependency dependency : (component.getDependencies())){
			comp.addDependency(dependency);
		}
		
		for(Parameter param : (component.getParameters())) {
			comp.addParameter(param);
		}
		
		for(String provInterface : component.getProvidedInterfaces()) {
			component.addProvidedInterface(provInterface);
		}
		
		LinkedHashMap<String,String> toAdd = component.getRequiredInterfaces();
		for(String str : toAdd.keySet()) {
			comp.addRequiredInterface(str, toAdd.get(str));
		}
		
	
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
