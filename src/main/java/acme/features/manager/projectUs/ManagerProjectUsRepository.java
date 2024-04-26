
package acme.features.manager.projectUs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projectus.ProjectUs;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectUsRepository extends AbstractRepository {

	@Query("select u from UserStory u where u.id = :id")
	UserStory findUserStorieById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select pu from ProjectUs pu where pu.project = :project and pu.userStory = :userStory")
	ProjectUs findProjectUserStoryByBoth(Project project, UserStory userStory);

	@Query("select p from Project p inner join ProjectUs pu on p = pu.project inner join UserStory u on pu.userStory = u where u = :userStory")
	Collection<Project> findProjectsByUserStory(UserStory userStory);

	@Query("select p from Project p where p.manager = :manager and p.draftMode = true")
	Collection<Project> findDraftModeProjectsByManager(Manager manager);

	@Query("select u from UserStory u inner join ProjectUs pu on u = pu.userStory inner join Project p on pu.project = p where p.id = :id")
	Collection<UserStory> findUserStoriesByProjectId(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findManagerById(int id);

}
