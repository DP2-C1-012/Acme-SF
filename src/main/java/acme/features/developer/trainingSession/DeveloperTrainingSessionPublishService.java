
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int sessionId;
		TrainingSession session;
		Developer developer;

		sessionId = super.getRequest().getData("id", int.class);
		session = this.repository.findTrainingSessionById(sessionId);
		developer = session == null ? null : session.getTrainingModule().getDeveloper();
		status = session != null && session.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingSessionById(id);
		//object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link");

		int trainingModule = super.getRequest().getData("module", int.class);
		TrainingModule tm = this.repository.findTrainingModuleById(trainingModule);
		object.setTrainingModule(tm);

	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		Collection<TrainingModule> tms;
		TrainingSession ts;
		tms = this.repository.findTrainingModulesNotPublishedByDeveloperId(super.getRequest().getPrincipal().getAccountId());
		ts = this.repository.findTrainingSessionByCode(object.getCode());

		if (!super.getBuffer().getErrors().hasErrors("code"))
			if (ts != null)
				super.state(ts.getId() == object.getId(), "code", "developer.training-session.form.error.duplicated-code");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfter(object.getStartPeriod(), MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 7, ChronoUnit.DAYS)), "startPeriod", "developer.training-session.form.error.startBeforeCreate");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod")) {
			super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "developer.training-session.form.error.endBeforeStart");
			if (!super.getBuffer().getErrors().hasErrors())
				super.state(MomentHelper.isAfter(object.getEndPeriod(), MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS)), "endPeriod", "developer.training-session.form.error.periodTooShort");
		}
		if (!super.getBuffer().getErrors().hasErrors("module"))
			super.state(tms.contains(object.getTrainingModule()), "module", "developer.training-session.form.error.trainingModule");

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link", "trainingModule", "draftMode");

		SelectChoices choices = new SelectChoices();

		Collection<TrainingModule> tms;
		tms = this.repository.findTrainingModulesNotPublishedByDeveloperId(super.getRequest().getPrincipal().getAccountId());

		for (final TrainingModule tm : tms)
			choices.add(String.valueOf(tm.getId()), tm.getCode(), false);

		dataset.put("modules", choices);
		super.getResponse().addData(dataset);

	}
}
