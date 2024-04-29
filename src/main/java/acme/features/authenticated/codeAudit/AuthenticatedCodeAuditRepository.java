
package acme.features.authenticated.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.projects.Project;

public interface AuthenticatedCodeAuditRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.published = true")
	Collection<CodeAudit> findPublishedCodeAudits();

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findOneCodeAuditByID(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllPublishedProjects();

}
