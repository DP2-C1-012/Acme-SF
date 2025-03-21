
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository	repository;

	// AbstractService interface ----------------------------------------------
	private String									trainingModuleId	= "trainingModuleId";
	private String									code				= "code";
	private String									startPeriod			= "startPeriod";
	private String									endPeriod			= "endPeriod";
	private String									location			= "location";
	private String									instructor			= "instructor";
	private String									email				= "email";
	private String									link				= "link";


	@Override
	public void authorise() {
		boolean status;
		TrainingModule object;
		int tmId;

		tmId = super.getRequest().getData("trainingModuleId", int.class);
		object = this.repository.findTrainingModuleById(tmId);

		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status && object.getDraftMode() == true);

	}

	@Override
	public void load() {
		TrainingSession object;
		int tmId;
		TrainingModule tm;

		tmId = super.getRequest().getData(this.trainingModuleId, int.class);
		super.getResponse().addGlobal(this.trainingModuleId, tmId);
		tm = this.repository.findTrainingModuleById(tmId);

		object = new TrainingSession();
		object.setDraftMode(true);
		object.setTrainingModule(tm);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, this.code, this.startPeriod, this.endPeriod, this.location, this.instructor, this.email, this.link);
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors(this.code)) {
			TrainingSession existing;

			existing = this.repository.findTrainingSessionByCode(object.getCode());
			super.state(existing == null, this.code, "developer.training-session.form.error.duplicate-code");
		}

		if (!super.getBuffer().getErrors().hasErrors(this.startPeriod))
			super.state(MomentHelper.isAfter(object.getStartPeriod(), MomentHelper.deltaFromMoment(object.getTrainingModule().getCreationMoment(), 7, ChronoUnit.DAYS)), "startPeriod", "developer.training-session.form.error.startBeforeCreate");
		if (!super.getBuffer().getErrors().hasErrors("endPeriod") && !super.getBuffer().getErrors().hasErrors("startPeriod")) {
			super.state(MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod()), "endPeriod", "developer.training-session.form.error.endBeforeStart");
			if (!super.getBuffer().getErrors().hasErrors())
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

		dataset = super.unbind(object, this.code, this.startPeriod, this.endPeriod, this.location, this.instructor, this.email, this.link);

		super.getResponse().addData(dataset);
	}

}
