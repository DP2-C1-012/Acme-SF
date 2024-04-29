
package acme.features.sponsor.dashboard;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.roles.Sponsor;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorship.sponsor = :sponsor and i.draftMode=false")
	Optional<List<Invoice>> findNumOfInvoices(Sponsor sponsor);

	@Query("select count(s) from Sponsorship s where s.sponsor = :sponsor and s.draftMode=false and s.link is not null  ")
	Optional<Integer> findNumOfSponsorshipsWithLink(Sponsor sponsor);

	@Query("select avg(s.amount.amount) from Sponsorship s where s.sponsor = :sponsor and s.draftMode=false and s.amount.currency = :currency")
	Optional<Double> findAverageSponsorshipAmount(Sponsor sponsor, String currency);

	@Query("select max(s.amount.amount) from Sponsorship s where s.sponsor = :sponsor and s.draftMode=false and s.amount.currency = :currency")
	Optional<Double> findMaxSponsorshipAmount(Sponsor sponsor, String currency);

	@Query("select min(s.amount.amount) from Sponsorship s where s.sponsor = :sponsor and s.draftMode=false and s.amount.currency = :currency")
	Optional<Double> findMinSponsorshipAmount(Sponsor sponsor, String currency);

	@Query("select stddev(s.amount.amount) from Sponsorship s where s.sponsor = :sponsor and s.draftMode=false and s.amount.currency = :currency")
	Optional<Double> findLinearDevSponsorshipAmount(Sponsor sponsor, String currency);

	@Query("select s from Sponsor s where s.userAccount.id = :id")
	Sponsor findOneSponsorByUserId(int id);

	@Query("select u from UserAccount u where u.id = :id")
	UserAccount findOneUserById(int id);

	@Query("select avg(i.quantity.amount) from Invoice i where i.sponsorship.sponsor = :sponsor and i.draftMode=false and i.tax.currency = :currency")
	Optional<Double> findAverageInvoiceQuantity(Sponsor sponsor, String currency);

	@Query("select max(i.quantity.amount) from Invoice i where i.sponsorship.sponsor = :sponsor and i.draftMode=false and i.tax.currency = :currency")
	Optional<Double> findMaxInvoiceQuantity(Sponsor sponsor, String currency);

	@Query("select min(i.quantity.amount) from Invoice i where i.sponsorship.sponsor = :sponsor and i.draftMode=false and i.tax.currency = :currency")
	Optional<Double> findMinInvoiceQuantity(Sponsor sponsor, String currency);

	@Query("select stddev(i.quantity.amount) from Invoice i where i.sponsorship.sponsor = :sponsor and i.draftMode=false and i.tax.currency = :currency")
	Optional<Double> findLinearDevInvoiceQuantity(Sponsor sponsor, String currency);

	@Query("select sc.acceptedCurrencies from SystemConfiguration sc")
	String findSystemConfigurationCurrencies();

}
