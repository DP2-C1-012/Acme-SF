
package acme.features.sponsor.dashboard;

import java.util.ArrayList;
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

	private String							euro				= "EUR";
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

		final double averageSponsorshipAmount = this.repository.findAverageSponsorshipAmount(sponsor, this.euro).orElse(0.0);
		final double maxSponsorshipAmount = this.repository.findMaxSponsorshipAmount(sponsor, this.euro).orElse(0.0);
		final double minSponsorshipAmount = this.repository.findMinSponsorshipAmount(sponsor, this.euro).orElse(0.0);
		final double devSponsorshipAmount = this.repository.findLinearDevSponsorshipAmount(sponsor, this.euro).orElse(0.0);
		dashboard.setAverageAmountOfSponsorships(averageSponsorshipAmount);
		dashboard.setMaximumAmountOfSponsorships(maxSponsorshipAmount);
		dashboard.setMinimumAmountOfSponsorships(minSponsorshipAmount);
		dashboard.setDeviationAmountOfSponsorships(devSponsorshipAmount);

		final double averageInvoiceQuantity = this.repository.findAverageInvoiceQuantity(sponsor, this.euro).orElse(0.0);
		final double maxInvoiceQuantity = this.repository.findMaxInvoiceQuantity(sponsor, this.euro).orElse(0.0);
		final double minInvoiceQuantity = this.repository.findMinInvoiceQuantity(sponsor, this.euro).orElse(0.0);
		final double devInvoiceQuantity = this.repository.findLinearDevInvoiceQuantity(sponsor, this.euro).orElse(0.0);
		dashboard.setAverageQuantityOfInvoices(averageInvoiceQuantity);
		dashboard.setMaximumQuantityOfInvoices(maxInvoiceQuantity);
		dashboard.setMinimumQuantityOfInvoices(minInvoiceQuantity);
		dashboard.setDeviationQuantityOfInvoices(devInvoiceQuantity);

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
		final List<String> acceptedCurrencies = new ArrayList<>();
		String currencies = this.repository.findSystemConfigurationCurrencies();
		String[] valuesArray = currencies.split(",");
		for (String value : valuesArray)
			acceptedCurrencies.add(value);

		dataset = super.unbind(object, "numberOfInvoicesWithATaskLessOrEqualThan21", "numberOfSponsorshipsWithALink", "averageAmountOfSponsorships", "deviationAmountOfSponsorships", "minimumAmountOfSponsorships", "maximumAmountOfSponsorships",
			"averageQuantityOfInvoices", "deviationQuantityOfInvoices", "minimumQuantityOfInvoices", "maximumQuantityOfInvoices");
		dataset.put("acceptedCurrencies", acceptedCurrencies);

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
