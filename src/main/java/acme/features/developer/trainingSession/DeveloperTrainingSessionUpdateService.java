
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionUpdateService extends AbstractService<Developer, TrainingSession> {

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
		TrainingSession ts;
		int tsId;

		tsId = super.getRequest().getData("id", int.class);
		System.out.println("load" + tsId);
		ts = this.repository.findTrainingSessionById(tsId);

		super.getBuffer().addData(ts);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "email", "link", "draftMode");

	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.getDraftMode(), "draftMode", "developer.training-session.form.error.draftMode");

		if (!super.getBuffer().getErrors().hasErrors("startPeriod"))
			super.state(MomentHelper.isAfter(object.getStartPeriod(), object.getTrainingModule().getCreationMoment()), "startMoment", "developer.training-session.form.error.startBeforeCreate");

		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("endPeriod")) {
			super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "developer.training-session.form.error.endBeforeStart");
			super.state(MomentHelper.isAfter(object.getEndPeriod(), MomentHelper.deltaFromMoment(object.getStartPeriod(), 7, ChronoUnit.DAYS)), "endPeriod", "developer.training-session.form.error.periodTooShort");
		}

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
		dataset = super.unbind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "email", "link", "draftMode");
		super.getResponse().addData(dataset);
	}
}
