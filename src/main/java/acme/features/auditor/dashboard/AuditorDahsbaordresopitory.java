
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.codeAudit.CodeAudit;

@Repository
public interface AuditorDahsbaordresopitory extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> findAuditorCodeAuditsByAuditorID(int id);
	@Query("SELECT ar FROM AuditRecord ar WHERE ar.codeAudit.auditor.id = :id")
	Collection<AuditRecord> findAuditorAuditRecorsByAuditorID(int id);
}
