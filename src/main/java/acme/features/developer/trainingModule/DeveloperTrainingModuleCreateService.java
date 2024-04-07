
package acme.features.developer.trainingModule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;
		developer = this.repository.getDeveloper(super.getRequest().getPrincipal().getActiveRoleId());
		object = new TrainingModule();
		object.setDraftMode(true);
		object.setDeveloper(developer);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;
		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "draftMode", "project");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Date creationMoment = object.getCreationMoment();
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;
			existing = this.repository.findTrainingModuleByCode(object.getCode());
			super.state(existing == null, "code", "developer.trainingModule.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			Date now = new Date();
			if (creationMoment == null || creationMoment.after(now))
				super.state(false, "creationMoment", "developer.trainingModule.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("details"))
			if (object.getDetails() == null || object.getDetails().isBlank() || object.getDetails().length() > 100)
				super.state(false, "details", "developer.trainingModule.form.error.code");
		if (!super.getBuffer().getErrors().hasErrors("difficultyLevel"))
			if (object.getDifficultyLevel() == null)
				super.state(false, "difficultyLevel", "developer.trainingModule.form.error.code");
		Date now = new Date(); // Obtener la fecha actual
		Date updateMoment = object.getUpdateMoment();
		if (updateMoment != null && updateMoment.after(now) && updateMoment.after(creationMoment))
			super.state(false, "updateMoment", "developer.trainingModule.form.error.code");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "draftMode", "project");
		super.getResponse().addData(dataset);
	}

}
