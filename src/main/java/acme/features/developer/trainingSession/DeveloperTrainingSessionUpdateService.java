
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.ValidatorService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

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
		status = session != null && session.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		TrainingSession ts;
		int tsId;

		tsId = super.getRequest().getData("id", int.class);
		ts = this.repository.findTrainingSessionById(tsId);

		super.getBuffer().addData(ts);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "email", "link", "draftMode");

		int trainingModuleId = super.getRequest().getData("trainingModule", int.class);
		TrainingModule tm = this.repository.findTrainingModuleById(trainingModuleId);
		object.setTrainingModule(tm);

	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		Collection<TrainingModule> tms;
		tms = this.repository.findTrainingModulesNotPublishedByDeveloperId(super.getRequest().getPrincipal().getAccountId());

		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfter(object.getStartPeriod(), object.getTrainingModule().getCreationMoment()), "startMoment", "developer.training-session.form.error.startBeforeCreate");

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod")) {
			super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "developer.training-session.form.error.endBeforeStart");
			super.state(MomentHelper.isAfter(object.getEndPeriod(), MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS)), "endPeriod", "developer.training-session.form.error.periodTooShort");
		}
		if (!super.getBuffer().getErrors().hasErrors("trainingModule"))
			super.state(tms.contains(object.getTrainingModule()), "trainingModule", "developer.training-session.form.error.trainingModule");

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link", "draftMode", "trainingModule");
		SelectChoices choices = new SelectChoices();

		Collection<TrainingModule> tms;
		tms = this.repository.findTrainingModulesNotPublishedByDeveloperId(super.getRequest().getPrincipal().getAccountId());

		for (final TrainingModule tm : tms)
			choices.add(String.valueOf(tm.getId()), tm.getCode(), false);

		dataset.put("modules", choices);

		super.getResponse().addData(dataset);
	}
}
