
package acme.features.sponsor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorDashboardRepository	repository;

	// AbstractService interface ----------------------------------------------

	public final List<String>				acceptedCurrencies	= new ArrayList<>();


	public void initializeAcceptedCurrencies() {
		String currencies = this.repository.findSystemConfigurationCurrencies();
		String[] valuesArray = currencies.split(",");
		for (String value : valuesArray)
			this.acceptedCurrencies.add(value.trim());
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final SponsorDashboard dashboard = new SponsorDashboard();
		Principal principal;
		int userId;
		principal = super.getRequest().getPrincipal();
		userId = principal.getAccountId();
		final Sponsor sponsor = this.repository.findOneSponsorByUserId(userId);
		String currencies = this.repository.findSystemConfigurationCurrencies();
		String[] valuesArray = currencies.split(",");
		for (String value : valuesArray)
			this.acceptedCurrencies.add(value);
		Collection<Double> avgSponsorship = new ArrayList<>();
		Collection<Double> maxSponsorship = new ArrayList<>();
		Collection<Double> minSponsorship = new ArrayList<>();
		Collection<Double> devSponsorship = new ArrayList<>();
		Collection<Double> avgInvoice = new ArrayList<>();
		Collection<Double> maxInvoice = new ArrayList<>();
		Collection<Double> minInvoice = new ArrayList<>();
		Collection<Double> devInvoice = new ArrayList<>();

		for (String currency : this.acceptedCurrencies) {
			final double averageSponsorshipAmount = this.repository.findAverageSponsorshipAmount(sponsor, currency).orElse(0.0);
			final double maxSponsorshipAmount = this.repository.findMaxSponsorshipAmount(sponsor, currency).orElse(0.0);
			final double minSponsorshipAmount = this.repository.findMinSponsorshipAmount(sponsor, currency).orElse(0.0);
			final double devSponsorshipAmount = this.repository.findLinearDevSponsorshipAmount(sponsor, currency).orElse(0.0);
			final double averageInvoiceQuantity = this.repository.findAverageInvoiceQuantity(sponsor, currency).orElse(0.0);
			final double maxInvoiceQuantity = this.repository.findMaxInvoiceQuantity(sponsor, currency).orElse(0.0);
			final double minInvoiceQuantity = this.repository.findMinInvoiceQuantity(sponsor, currency).orElse(0.0);
			final double devInvoiceQuantity = this.repository.findLinearDevInvoiceQuantity(sponsor, currency).orElse(0.0);

			avgSponsorship.add(averageSponsorshipAmount);
			maxSponsorship.add(maxSponsorshipAmount);
			minSponsorship.add(minSponsorshipAmount);
			devSponsorship.add(devSponsorshipAmount);
			avgInvoice.add(averageInvoiceQuantity);
			maxInvoice.add(maxInvoiceQuantity);
			minInvoice.add(minInvoiceQuantity);
			devInvoice.add(devInvoiceQuantity);
		}
		dashboard.setAverageAmountOfSponsorships(avgSponsorship);
		dashboard.setMaximumAmountOfSponsorships(maxSponsorship);
		dashboard.setMinimumAmountOfSponsorships(minSponsorship);
		dashboard.setDeviationAmountOfSponsorships(devSponsorship);

		dashboard.setAverageQuantityOfInvoices(avgInvoice);
		dashboard.setMaximumQuantityOfInvoices(maxInvoice);
		dashboard.setMinimumQuantityOfInvoices(minInvoice);
		dashboard.setDeviationQuantityOfInvoices(devInvoice);

		List<Invoice> inv = this.repository.findNumOfInvoices(sponsor).orElse(new ArrayList<>());
		final int numInvoicesWithTaxLessOrEqualThan21 = this.getNInvoicesWithTaxLessOrEqualThan21(inv);
		final int numSponsorshipsWithLink = this.repository.findNumOfSponsorshipsWithLink(sponsor).orElse(0);
		dashboard.setNumberOfInvoicesWithATaskLessOrEqualThan21(numInvoicesWithTaxLessOrEqualThan21);
		dashboard.setNumberOfSponsorshipsWithALink(numSponsorshipsWithLink);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "numberOfInvoicesWithATaskLessOrEqualThan21", "numberOfSponsorshipsWithALink", "averageAmountOfSponsorships", "deviationAmountOfSponsorships", "minimumAmountOfSponsorships", "maximumAmountOfSponsorships",
			"averageQuantityOfInvoices", "deviationQuantityOfInvoices", "minimumQuantityOfInvoices", "maximumQuantityOfInvoices");
		dataset.put("acceptedCurrencies", this.acceptedCurrencies);

		super.getResponse().addData(dataset);
	}

	public int getNInvoicesWithTaxLessOrEqualThan21(final List<Invoice> inv) {
		int result = 0;
		if (!inv.isEmpty())
			for (final Invoice i : inv) {
				double tax = i.getTax().getAmount();
				double quantity = i.getQuantity().getAmount();
				if (quantity > 0 && tax / quantity <= 0.21)
					result++;
			}
		return result;
	}
}
