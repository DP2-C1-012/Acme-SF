
package acme.features.any.training_module;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule = :trainingModule")
	Collection<TrainingSession> findTrainingSessionsByTrainingModule(TrainingModule trainingModule);

	@Query("select tm from TrainingModule tm where tm.draftMode = false")
	Collection<TrainingModule> findPublishedTrainingModules();
}
