package CompEditor.backend.ComponentEditor;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import Controller.ComponentsController;

@SpringBootApplication
@ComponentScan(basePackageClasses = ComponentsController.class)
public class ComponentEditorApplication {

	public static void main(final String[] args) throws IOException {

		/*
		 * String fileSeperator = System.getProperty("file.separator"); String
		 * realativPath = "tmp"+ fileSeperator + "ComponentDB.txt"; File ComponentsDB =
		 * new File(realativPath); if(!ComponentsDB.createNewFile()) {
		 *
		 * }
		 */

		SpringApplication.run(ComponentEditorApplication.class, args);
	}

}
