
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudit.CodeAudit;

public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT ca FROM CodeAudit ca WHERE ca.auditor.id = :id")
	Collection<CodeAudit> FindCodeAuditsByAuditorID(int id);
}
