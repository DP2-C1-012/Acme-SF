
package acme.features.manager.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(u) from UserStory u where u.manager = :manager and u.draftMode=false")
	Optional<Integer> findNumOfUserStories(Manager manager);

	@Query("select avg(u.estimatedCost.amount) from UserStory u where u.manager = :manager and u.draftMode=false")
	Optional<Integer> findAverageUserStoryCost(Manager manager);

	@Query("select stddev(u.estimatedCost.amount) from UserStory u where u.manager = :manager and u.draftMode=false")
	Optional<Integer> findDevUserStoryCost(Manager manager);

	@Query("select min(u.estimatedCost.amount) from UserStory u where u.manager = :manager and u.draftMode=false")
	Optional<Integer> findMinUserStoryCost(Manager manager);

	@Query("select max(u.estimatedCost.amount) from UserStory u where u.manager = :manager and u.draftMode=false")
	Optional<Integer> findMaxUserStoryCost(Manager manager);

	@Query("select avg(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Integer> findAverageProjectCost(Manager manager);

	@Query("select stddev(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Integer> findDevProjectCost(Manager manager);

	@Query("select min(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Integer> findMinProjectCost(Manager manager);

	@Query("select max(p.cost.amount) from Project p where p.manager = :manager and p.draftMode=false")
	Optional<Integer> findMaxProjectCost(Manager manager);

	@Query("select m from Manager m where m.userAccount.id = :id")
	Manager findManagerByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findUserAccountById(int id);
}
