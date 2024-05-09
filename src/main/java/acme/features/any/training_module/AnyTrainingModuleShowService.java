
package acme.features.any.training_module;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {

	@Autowired
	protected AnyTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		TrainingModule object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);
		super.getResponse().setAuthorised(!object.getDraftMode());
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
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link");
		dataset.put("degree", object.getDeveloper().getDegree());
		dataset.put("time", object.getEstimatedTotalTime() + " horas");
		dataset.put("projectCode", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}
}
