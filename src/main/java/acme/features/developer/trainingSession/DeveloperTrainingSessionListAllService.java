
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
public class DeveloperTrainingSessionListAllService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<TrainingSession> objects;
		Collection<TrainingModule> tm;
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		tm = this.repository.listMyTrainingModule(userAccountId);
		objects = tm.stream().map(t -> this.repository.findAllTrainingSessionsBytrainingModuleId(t.getId())).flatMap(Collection::stream).toList();
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "location", "instructor", "draftMode");
		dataset.put("module", object.getTrainingModule().getCode());
		super.getResponse().addData(dataset);
	}

}
