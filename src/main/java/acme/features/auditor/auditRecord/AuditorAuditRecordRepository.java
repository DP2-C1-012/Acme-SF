
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.codeAudit.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.id = :id")
	CodeAudit findOneCodeAuditByID(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.codeAudit.id = :id")
	Collection<AuditRecord> findAuditRecordsByCodeAuditID(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.id = :id")
	AuditRecord findOneAuditRecordByID(int id);

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> findCodeAuditsOfAuditorByAuditorID(int id);

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id AND ca.published = false")
	Collection<CodeAudit> findNotPublishedCodeAuditsOfAuditorByAuditorID(int id);

	@Query("SELECT ar FROM AuditRecord ar WHERE ar.code = :code")
	AuditRecord findOneAuditRecordByCode(String code);
}
