
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;
		developer = this.repository.getDeveloper(super.getRequest().getPrincipal().getActiveRoleId());
		object = new TrainingModule();
		object.setDraftMode(true);
		object.setDeveloper(developer);
		Date moment = MomentHelper.getCurrentMoment();
		object.setCreationMoment(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link");

		int projectId = super.getRequest().getData("project", int.class);
		Project p = this.repository.findOneProjectById(projectId);
		object.setProject(p);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Date m = MomentHelper.getCurrentMoment();
		TrainingModule tm;
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();
		tm = this.repository.findTrainingModuleByCode(object.getCode());

		if (!super.getBuffer().getErrors().hasErrors("code"))
			if (tm != null)
				super.state(tm.getId() == object.getId(), "code", "developer.training-module.form.error.duplicated-code");

		if (!super.getBuffer().getErrors().hasErrors("updateMoment") && !(object.getUpdateMoment() == null)) {
			super.state(m.after(object.getUpdateMoment()), "updateMoment", "developer.training-module.form.error.updateMoment");
			super.state(object.getUpdateMoment().after(object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.updateMoment-after-createMoment");
		}

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(projects.contains(object.getProject()), "project", "developer.training-module.form.error.project");

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		object.setCreationMoment(MomentHelper.getCurrentMoment());

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "project");

		SelectChoices difficultyLevel = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		final SelectChoices choices = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();

		for (final Project p : projects)
			if (object.getProject() != null && object.getProject().getId() == p.getId())
				choices.add(String.valueOf(p.getId()), p.getCode() + " - " + p.getTitle(), true);
			else
				choices.add(String.valueOf(p.getId()), p.getCode() + " - " + p.getTitle(), false);
		dataset.put("difficultyLevels", difficultyLevel);
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
