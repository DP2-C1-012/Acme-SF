
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	@Autowired
	protected ManagerProjectListMineService	listService;

	@Autowired
	protected ManagerProjectShowService		showService;

	@Autowired
	protected ManagerProjectCreateService	createService;

	@Autowired
	protected ManagerProjectDeleteService	deleteService;

	@Autowired
	protected ManagerProjectUpdateService	updateService;

	@Autowired
	protected ManagerProjectPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
