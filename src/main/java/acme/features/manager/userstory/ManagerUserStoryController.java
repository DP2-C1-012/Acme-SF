
package acme.features.manager.userstory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryListAllService	listAllService;

	@Autowired
	protected ManagerUserStoryListMineService	listMineService;

	@Autowired
	protected ManagerUserStoryShowService		showService;

	@Autowired
	protected ManagerUserStoryCreateService		createService;

	@Autowired
	protected ManagerUserStoryUpdateService		updateService;

	@Autowired
	protected ManagerUserStoryDeleteService		deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listMineService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
