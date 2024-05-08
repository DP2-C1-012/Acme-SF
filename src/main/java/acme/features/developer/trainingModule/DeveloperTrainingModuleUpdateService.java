
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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
		Date moment = MomentHelper.getCurrentMoment();
		object.setUpdateMoment(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "link");

		int projectId = super.getRequest().getData("project", int.class);
		Project p = this.repository.findOneProjectById(projectId);
		object.setProject(p);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Date m = MomentHelper.getCurrentMoment();

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule tm;
			tm = this.repository.findTrainingModuleByCode(object.getCode());
			super.state(tm == null || tm.getId() == object.getId(), "code", "developer.trainingModule.form.error.code");

		}
		if (!super.getBuffer().getErrors().hasErrors("creationMoment"))
			super.state(m.after(object.getCreationMoment()), "creationMoment", "developer.trainingModule.form.error.creationMoment");
		if (!super.getBuffer().getErrors().hasErrors("updateMoment") && !(object.getUpdateMoment() == null))
			super.state(object.getUpdateMoment().after(object.getCreationMoment()), "updateMoment", "developer.trainingModule.form.error.updateMoment-after-createMoment");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		object.setUpdateMoment(MomentHelper.getCurrentMoment());
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "project");

		SelectChoices choicesDifficultyLevel = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		SelectChoices choicesProject = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();

		for (final Project p : projects)
			choicesProject.add(String.valueOf(p.getId()), p.getCode() + " - " + p.getTitle(), false);

		dataset.put("projects", choicesProject);
		dataset.put("difficultyLevels", choicesDifficultyLevel);

		super.getResponse().addData(dataset);
	}
}
