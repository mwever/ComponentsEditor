package Controller;

import java.awt.PageAttributes.MediaType;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import Entity.Component;
import Service.ComponentService;

@RestController
@ComponentScan(basePackageClasses = ComponentService.class)
@RequestMapping("/components")
public class ComponentsController{
	@Autowired
	private ComponentService componentService;
	
	
	@RequestMapping(method =RequestMethod.GET)
	public Collection<Component> getAllComponents(){
		return this.componentService.getAllComponents();
	}
	
	@RequestMapping(value = "/{compName}", method = RequestMethod.GET)
	public Component getComponentByName(@PathVariable("compName") String name) {
		return this.componentService.getComponentByName(name);
	}
	
	@RequestMapping(value = "/{compName}", method = RequestMethod.DELETE)
	public void deleteComponentbyName(@PathVariable("compName") String name) {
		this.componentService.removeComponentByName(name);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateComponent(@RequestBody Component component) {
		this.componentService.updateComponent(component);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void insertComponent(@RequestBody Component component) {
		this.componentService.insertComponent(component);
	}
}
