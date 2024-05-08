
package acme.features.developer.dashboard;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
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
		int id;
		DeveloperDashboard developerDashboard;
		Integer numOfTrainingModuleOfDeveloper;
		Integer numTrainingSessionsWithLink;
		Double averageTrainingModuleTime;
		Integer minTrainingModuleTime;
		Integer maxTrainingModuleTime;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<TrainingModule> trainingModules = this.repository.listMyTrainingModule(id);
		System.out.println(trainingModules);

		numOfTrainingModuleOfDeveloper = this.repository.findNumOfTrainingModuleOfDeveloper(id);
		numTrainingSessionsWithLink = this.repository.numberOfTrainingSessionsWithLink(id);
		averageTrainingModuleTime = trainingModules.stream().mapToInt(tm -> this.getEstimatedTotalTime(tm)).average().orElse(0.0);
		minTrainingModuleTime = trainingModules.stream().mapToInt(tm -> this.getEstimatedTotalTime(tm)).min().orElse(0);
		maxTrainingModuleTime = trainingModules.stream().mapToInt(tm -> this.getEstimatedTotalTime(tm)).max().orElse(0);

		developerDashboard = new DeveloperDashboard();

		developerDashboard.setNumOfTrainingModuleOfDeveloper(numOfTrainingModuleOfDeveloper);
		developerDashboard.setNumTrainingSessionsWithLink(numTrainingSessionsWithLink);
		developerDashboard.setAverageTrainingModuleTime(averageTrainingModuleTime);
		developerDashboard.setMinTrainingModuleTime(minTrainingModuleTime);
		developerDashboard.setMaxTrainingModuleTime(maxTrainingModuleTime);

		super.getBuffer().addData(developerDashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "numOfTrainingModuleOfDeveloper", "numTrainingSessionsWithLink", "averageTrainingModuleTime", "minTrainingModuleTime", "maxTrainingModuleTime");
		super.getResponse().addData(dataset);
	}

}
