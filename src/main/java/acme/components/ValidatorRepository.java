
package acme.components;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.trainingSession.TrainingSession;

@Repository
public interface ValidatorRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorship.id = :id and i.draftMode = false")
	Collection<Invoice> findPublishedInvoicesBySponsorshipId(int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id and i.draftMode = true")
	Collection<Invoice> findInvoicesNotPublishedBySponsorshipId(int id);

	@Query("select sc.acceptedCurrencies from SystemConfiguration sc")
	String findSystemConfigurationCurrencies();

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :id and ts.draftMode = true")
	Collection<TrainingSession> findTrainingSessionsNotPublishedByTrainingModuleId(int id);
}
