
package acme.features.any.training_module;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.trainingModule.TrainingModule;

@Controller
public class AnyTrainingModuleController extends AbstractController<Any, TrainingModule> {

	@Autowired
	protected AnyTrainingModuleListService	listService;

	@Autowired
	protected AnyTrainingModuleShowService	showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
