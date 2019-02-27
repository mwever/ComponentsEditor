package Service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import Data.DataCollectionComponent;
import Data.DataCollectionComponentFile;
import Entity.Component;

@Service
@ComponentScan(basePackageClasses = DataCollectionComponent.class)
@Qualifier("FileData")
public class ComponentService {
	
	@Autowired
	private DataCollectionComponent dataCollectionComponent;
	
	public Collection<Component> getAllComponents(){
		return this.dataCollectionComponent.getAllComponents();
	}
	
	public Component getComponentByName(String name) {
		return dataCollectionComponent.getComponentByName(name);
		/*
		 * if(componentData.getAllComponents().contains(name)) { return
		 * componentData.getComponentByName(name); } else {
		 * System.out.println("The Component does not exsit"); return null; }
		 */
	}

	public void removeComponentByName(String name) {
		this.dataCollectionComponent.removeComponentByName(name);
	}
	
	public void updateComponent(Component component) {
		dataCollectionComponent.updateComponent(component);
	}

	public void insertComponent(Component component) {
		dataCollectionComponent.insertComponent(component);
	}
}
