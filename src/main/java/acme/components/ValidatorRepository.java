
package acme.components;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;

@Repository
public interface ValidatorRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorship.id = :id and i.draftMode = false")
	Collection<Invoice> findPublishedInvoicesBySponsorshipId(int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id and i.draftMode = true")
	Collection<Invoice> findInvoicesNotPublishedBySponsorshipId(int id);

	@Query("select sc.acceptedCurrencies from SystemConfiguration sc")
	String findSystemConfigurationCurrencies();
}
