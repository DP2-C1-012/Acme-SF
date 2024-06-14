
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("SELECT tm FROM TrainingModule tm WHERE tm.draftMode = false AND tm.developer.id = :id")
	Collection<TrainingModule> listMyPublishTrainingModule(int id);

	@Query("select count(tm) from TrainingModule tm where tm.draftMode = false AND tm.developer.id = :id")
	Integer findNumOfTrainingModuleOfDeveloper(int id);

	@Query("select count(ts) from TrainingSession ts where ts.trainingModule.draftMode = false AND ts.trainingModule.developer.id = :id AND ts.link is not null")
	Integer numberOfTrainingSessionsWithLink(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.draftMode = false AND ts.trainingModule = :trainingModule")
	Collection<TrainingSession> findTrainingSessionsByTrainingModule(TrainingModule trainingModule);

}
