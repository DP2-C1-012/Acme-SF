
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		TrainingModule object;
		int trainingModuleId;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		object = this.repository.findTrainingModuleById(trainingModuleId);
		System.out.println(object);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId);

	}

	@Override
	public void load() {
		TrainingSession object;
		int trainingModuleId;
		TrainingModule tm;

		object = new TrainingSession();
		object.setDraftMode(true);
		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		System.out.println("moduleIdcc: " + trainingModuleId);
		tm = this.repository.findTrainingModuleById(trainingModuleId);
		object.setTrainingModule(tm);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "email", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.training-session.form.error.duplicateCode");
		}

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
		dataset = super.unbind(object, "code", "startPeriod", "instructor", "location", "endPeriod", "email", "link");
		super.getResponse().addGlobal("trainingModuleId", super.getRequest().getData("trainingModuleId", int.class));
		super.getResponse().addData(dataset);
	}

}
