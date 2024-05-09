
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
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository	repository;

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
		System.out.println(sessions);
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
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "estimatedTotalTime", "project", "draftMode", "developer");

		SelectChoices choicesDifficultyLevel = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		SelectChoices choicesProject = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();

		for (final Project p : projects) {
			boolean isSelected = this.validator.isSelectedProject(object, p);
			choicesProject.add(String.valueOf(p.getId()), p.getCode() + " -> " + p.getTitle(), isSelected);
		}
		dataset.put("time", object.getEstimatedTotalTime() + " horas");
		dataset.put("projects", choicesProject);
		dataset.put("difficultyLevels", choicesDifficultyLevel);

		super.getResponse().addData(dataset);

	}

}
