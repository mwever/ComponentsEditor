package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Controller.RepositoryController.BufferRepo;
import Service.State;

@RestController
@ComponentScan(basePackageClasses = State.class)

@RequestMapping("/state")
public class StateController {

	@Autowired private State currentState;
	
	@PostMapping
	public void  save(@RequestBody BufferRepo buffer) {
		
	}
}
