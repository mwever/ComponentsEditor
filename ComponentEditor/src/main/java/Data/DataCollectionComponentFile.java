package Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import hasco.model.Component;
import hasco.model.Dependency;
import hasco.model.Parameter;



@Repository
@Qualifier("FileData")
public class DataCollectionComponentFile implements DataCollectionComponent {
	private HashMap<String,Component> components = new HashMap<>();
	
	
	/* (non-Javadoc)
	 * @see Data.ComponentData#getAllComponents()
	 */
	@Override
	public Collection<Component> getAllComponents(){
		return this.components.values();
	}
	
	/* (non-Javadoc)
	 * @see Data.ComponentData#getComponentByName(java.lang.String
	 * )
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
		
		if(!component.getDependencies().isEmpty()) {
			for(Dependency dependency : (component.getDependencies())){
				if(!(comp.getDependencies().contains(dependency))) {
					comp.addDependency(dependency);
				}
			}
		}
		else {
			if(!comp.getDependencies().isEmpty()) {
				comp.getDependencies().clear();
			}
		}
		
		if(!component.getParameters().isEmpty()) {
			for(Parameter param : (component.getParameters())) {
				if(! comp.getParameters().contains(param)) {
					comp.addParameter(param);
				}
			}
		}
		else {
			if(comp.getParameters() != null) {
				comp.getParameters().clear();
			}
		}
		
		if(!component.getProvidedInterfaces().isEmpty()) {
			for(String provInterface : component.getProvidedInterfaces()) {
				if(!comp.getProvidedInterfaces().contains(provInterface)) {
					comp.addProvidedInterface(provInterface);
				}
			}
		}
		else {
			if(!comp.getProvidedInterfaces().isEmpty()) {
				comp.getProvidedInterfaces().clear();
			}
		}
		
		if(!component.getRequiredInterfaces().isEmpty()) {
			LinkedHashMap<String,String> toAdd = component.getRequiredInterfaces();
			for(String str : toAdd.keySet()) {
				if(!comp.getRequiredInterfaces().containsKey(str)) {
					comp.addRequiredInterface(str, toAdd.get(str));
				}
			}
		}
		else {
			if(!comp.getRequiredInterfaces().isEmpty()) {
				comp.getRequiredInterfaces().clear();
			}
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
