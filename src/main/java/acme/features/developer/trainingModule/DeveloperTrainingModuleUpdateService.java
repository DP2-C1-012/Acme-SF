
package acme.features.developer.trainingModule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleUpdateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	private DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId && object.getDraftMode());
	}
	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link");
		object.setProject(project);

	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Date creationMoment = object.getCreationMoment();

		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			Date now = new Date();
			if (creationMoment == null || creationMoment.after(now))
				super.state(false, "creationMoment", "developer.trainingModule.form.error.creationMoment");
		}
		if (!super.getBuffer().getErrors().hasErrors("details"))
			if (object.getDetails() == null || object.getDetails().isBlank() || object.getDetails().length() > 100)
				super.state(false, "details", "developer.trainingModule.form.error.details");
		if (!super.getBuffer().getErrors().hasErrors("difficultyLevel"))
			if (object.getDifficultyLevel() == null)
				super.state(false, "difficultyLevel", "developer.trainingModule.form.error.difficultyLevel");
		Date now = new Date(); // Obtener la fecha actual
		Date updateMoment = object.getUpdateMoment();
		if (updateMoment != null && updateMoment.after(now) && updateMoment.after(creationMoment))
			super.state(false, "updateMoment", "developer.trainingModule.form.error.updateMoment");
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

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link");

		SelectChoices choices;
		choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}
}
