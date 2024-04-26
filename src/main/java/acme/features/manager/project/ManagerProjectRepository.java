
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projectus.ProjectUs;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select u from UserStory u inner join ProjectUs pu on u = pu.userStory inner join Project p on pu.project = p where p.id = :id")
	Collection<UserStory> findUserStoriesByProjectId(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select p from Project p where p.code = :code")
	Project findProjectByCode(String code);

	@Query("select pus from ProjectUs pus where pus.project = :object")
	Collection<ProjectUs> findProjectUserStoriesByProject(Project object);

}
