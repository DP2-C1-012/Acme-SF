
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

		tmId = super.getRequest().getData("trainingModuleId", int.class);
		objects = this.repository.findAllTrainingSessionsBytrainingModuleId(tmId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "draftMode");

		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("trainingModuleId", object.getTrainingModule().getId());
	}

	@Override
	public void unbind(final Collection<TrainingSession> objects) {
		assert objects != null;
		int tmId;

		tmId = super.getRequest().getData("trainingModuleId", int.class);
		super.getResponse().addGlobal("trainingModuleId", tmId);
	}

}
