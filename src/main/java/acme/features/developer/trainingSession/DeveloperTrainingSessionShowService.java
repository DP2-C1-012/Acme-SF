
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.ValidatorService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionShowService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository	repository;

	@Autowired
	protected ValidatorService					validator;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		TrainingSession session;
		Developer developer;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findTrainingSessionById(sessionId);
		developer = session == null ? null : session.getTrainingModule().getDeveloper();
		status = session != null && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link", "draftMode");
		SelectChoices choices = new SelectChoices();

		Collection<TrainingModule> tms;
		tms = this.repository.findTrainingModulesNotPublishedByDeveloperId(super.getRequest().getPrincipal().getAccountId());

		for (final TrainingModule tm : tms) {
			boolean isSelected = this.validator.isSelectedTrainingModule(object, tm);
			choices.add(String.valueOf(tm.getId()), tm.getCode(), isSelected);
		}

		dataset.put("trainingModule", object.getTrainingModule().getId());

		dataset.put("module", object.getTrainingModule().getCode());

		dataset.put("modules", choices);

		super.getResponse().addData(dataset);
	}

}
