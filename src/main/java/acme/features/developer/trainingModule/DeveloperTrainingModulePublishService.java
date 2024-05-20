
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.ValidatorService;
import acme.entities.projects.Project;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	private DeveloperTrainingModuleRepository	repository;

	@Autowired
	protected ValidatorService					validator;


	@Override
	public void authorise() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		super.getResponse().setAuthorised(object.getDeveloper().getUserAccount().getId() == userAccountId);
	}

	public Integer getEstimatedTotalTime(final TrainingModule tm) {
		int totalTime = 0;

		List<TrainingSession> sessions = this.repository.findTrainingSessionsByTrainingModule(tm).stream().toList();
		for (TrainingSession session : sessions)
			totalTime += session.getEndPeriod().getTime() / 3600000 - session.getStartPeriod().getTime() / 3600000;
		return totalTime;
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);

		int estimatedTotalTime = this.getEstimatedTotalTime(object);
		object.setEstimatedTotalTime(estimatedTotalTime);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link");
		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findOneProjectById(projectId);
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		Collection<Project> projects;

		TrainingModule tm;
		tm = this.repository.findTrainingModuleByCode(object.getCode());

		projects = this.repository.findAllPublishedProjects();

		if (!super.getBuffer().getErrors().hasErrors())
			super.state(this.validator.validateExistsPublishedTrainingSessions(object.getId()), "*", "developer.training-module.form.error.draftMode-trainingSessions");
		if (!super.getBuffer().getErrors().hasErrors("code") && tm != null)
			super.state(tm.getId() == object.getId(), "code", "developer.training-module.form.error.duplicated-code");
		if (!super.getBuffer().getErrors().hasErrors("updateMoment") && !(object.getUpdateMoment() == null) && !(object.getCreationMoment() == null))
			super.state(object.getUpdateMoment().after(object.getCreationMoment()), "updateMoment", "developer.training-module.form.error.updateMoment-after-createMoment");
		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(projects.contains(object.getProject()), "project", "developer.training-module.form.error.project");

	}
	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "draftMode");

		final SelectChoices choices = new SelectChoices();
		SelectChoices difficultyLevel = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();
		for (final Project p : projects)
			if (object.getProject() != null && object.getProject().getId() == p.getId())
				choices.add(String.valueOf(p.getId()), p.getCode(), true);
			else
				choices.add(String.valueOf(p.getId()), p.getCode(), false);

		dataset.put("time", object.getEstimatedTotalTime() + " horas");
		dataset.put("difficultyLevels", difficultyLevel);
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);

	}
}
