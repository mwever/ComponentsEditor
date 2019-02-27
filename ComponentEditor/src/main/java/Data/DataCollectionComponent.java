package Data;

import java.util.Collection;

import Entity.Component;

public interface DataCollectionComponent {

	Collection<Component> getAllComponents();

	Component getComponentByName(String name);

	void removeComponentByName(String name);

	void updateComponent(Component component);

	void insertComponent(Component component);

}