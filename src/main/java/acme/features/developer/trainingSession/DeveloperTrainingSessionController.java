
package acme.features.developer.trainingSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionListService		listTrainingSessions;

	@Autowired
	private DeveloperTrainingSessionShowService			showService;

	@Autowired
	private DeveloperTrainingSessionCreateService		createService;

	@Autowired
	private DeveloperTrainingSessionUpdateService		updateService;

	@Autowired
	private DeveloperTrainingSessionDeleteService		deleteService;

	@Autowired
	private DeveloperTrainingSessionPublishService		publishService;

	@Autowired
	protected DeveloperTrainingSessionListAllService	listAllService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listTrainingSessions);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("list-all", "list", this.listAllService);
	}

}
