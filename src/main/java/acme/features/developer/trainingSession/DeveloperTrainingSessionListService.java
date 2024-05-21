
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		TrainingModule object;
		int tmId;

		tmId = super.getRequest().getData("trainingModuleId", int.class);
		object = this.repository.findTrainingModuleById(tmId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		int tmId;
		Boolean mode;

		tmId = super.getRequest().getData("trainingModuleId", int.class);
		objects = this.repository.findAllTrainingSessionsBytrainingModuleId(tmId);
		mode = this.repository.findTrainingModuleById(tmId).getDraftMode();
		super.getResponse().addGlobal("mode", mode);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;
		int tmId;

		tmId = super.getRequest().getData("trainingModuleId", int.class);
		super.getResponse().addGlobal("trainingModuleId", tmId);

		dataset = super.unbind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "draftMode");
		dataset.put("module", object.getTrainingModule().getCode());
		dataset.put("trainingModuleId", tmId);

		super.getResponse().addData(dataset);
	}

}
