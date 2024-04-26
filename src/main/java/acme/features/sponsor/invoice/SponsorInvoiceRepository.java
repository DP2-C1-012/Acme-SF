
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findInvoicesBySponsorship(int id);

	@Query("select i from Invoice i where i.sponsorship.sponsor = :sponsor")
	Collection<Invoice> findInvoicesBySponsor(Sponsor sponsor);

	@Query("select i from Invoice i where i.id = :id")
	Invoice findInvoiceById(int id);
	@Query("select i from Invoice i where i.code = :code")
	Invoice findInvoiceByCode(String code);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findSponsorById(int id);

	@Query("select sc.acceptedCurrencies from SystemConfiguration sc")
	String findSystemConfigurationCurrencies();
}
