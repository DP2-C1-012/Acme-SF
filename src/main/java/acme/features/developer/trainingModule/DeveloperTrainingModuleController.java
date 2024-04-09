
package acme.features.developer.trainingModule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingModuleController extends AbstractController<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleListService	listTrainingModule;

	@Autowired
	private DeveloperTrainingModuleShowService		showService;

	@Autowired
	protected DeveloperTrainingModuleCreateService	createService;

	@Autowired
	protected DeveloperTrainingModuleDeleteService	deleteService;

	@Autowired
	protected DeveloperTrainingModulePublishService	publishService;

	@Autowired
	protected DeveloperTrainingModuleUpdateService	updateService;
	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listTrainingModule);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);

	}

}
