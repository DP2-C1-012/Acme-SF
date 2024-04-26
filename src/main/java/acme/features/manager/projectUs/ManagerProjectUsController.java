
package acme.features.manager.projectUs;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projectus.ProjectUs;
import acme.roles.Manager;

@Controller
public class ManagerProjectUsController extends AbstractController<Manager, ProjectUs> {

	@Autowired
	protected ManagerProjectUsDeleteService deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("delete", this.deleteService);
	}
}
