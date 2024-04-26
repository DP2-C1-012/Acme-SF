
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.developer.id = :id")
	Collection<TrainingModule> listMyTrainingModule(int id);

	@Query("select count(tm) from TrainingModule tm where tm.developer.id = :id")
	Integer findNumOfTrainingModuleOfDeveloper(int id);

	@Query("select count(ts) from TrainingSession ts where ts.trainingModule.developer.id = :id and ts.link is not null")
	Integer numberOfTrainingSessionsWithLink(int id);

}
