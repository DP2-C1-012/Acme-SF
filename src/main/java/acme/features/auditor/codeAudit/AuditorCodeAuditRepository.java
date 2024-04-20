
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditsByAuditorID(int id);

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findOneCodeAuditByID(int id);

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findAuditorByID(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllPublishedProjects();

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectByID(int id);

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.codeAudit.id = :id")
	Collection<AuditRecord> findCodeAuditAuditRecordsByCodeAuditID(int id);
}
