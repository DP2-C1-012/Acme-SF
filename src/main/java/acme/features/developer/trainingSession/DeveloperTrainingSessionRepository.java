
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findTrainingSessionById(int id);

	@Query("select ts from TrainingSession ts")
	Collection<TrainingSession> findAllTrainingSessions();

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :id")
	Collection<TrainingSession> findAllTrainingSessionsBytrainingModuleId(int id);

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("select d from Developer d where d.id = :id")
	Developer getDeveloper(int id);

	@Query("select ts from TrainingSession ts where ts.code = :code")
	TrainingSession findTrainingSessionByCode(String code);

}
