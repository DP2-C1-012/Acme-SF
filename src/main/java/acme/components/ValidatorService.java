
package acme.components;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.entities.invoices.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingSession.TrainingSession;

@Service
public class ValidatorService {

	@Autowired
	private ValidatorRepository repository;


	public boolean validateMoneyQuantity(final Money value) {
		//Validate here money range
		return value.getAmount() >= 0 && value.getAmount() <= 1000000;
	}
	public boolean validateMoneyCurrency(final Money value) {
		//Validate here currency
		String currencies = this.repository.findSystemConfigurationCurrencies();
		return currencies.contains(value.getCurrency());
	}
	public boolean validateDate(final Date value) {
		LocalDateTime maxDateTime = LocalDateTime.of(2100, Month.DECEMBER, 31, 23, 59);
		LocalDateTime minDateTime = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
		final Date max = Date.from(maxDateTime.atZone(ZoneId.systemDefault()).toInstant());
		final Date min = Date.from(minDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return MomentHelper.isAfterOrEqual(value, min) && MomentHelper.isBeforeOrEqual(value, max);
	}
	public boolean validateMoment(final Date startDate, final Date endDate) {
		//Validate here moment 1 month at least
		Date minimum = MomentHelper.deltaFromMoment(startDate, 30, ChronoUnit.DAYS);
		return MomentHelper.isAfterOrEqual(endDate, minimum);
	}
	public boolean validateFuture(final Date registration) {
		//Validate here moment is past
		return MomentHelper.isBefore(registration, MomentHelper.getCurrentMoment());
	}
	public boolean validateCurrencyChange(final Money value, final int id) {
		//Validate here that once a published invoice has a currency, the sponsorship currency cannot be changed
		boolean result = true;
		Collection<Invoice> pInvoices = this.repository.findPublishedInvoicesBySponsorshipId(id);
		if (!pInvoices.isEmpty())
			for (final Invoice i : pInvoices)
				result = result && i.getQuantity().getCurrency().equals(value.getCurrency()) && i.getTax().getCurrency().equals(value.getCurrency());
		return result;
	}
	public boolean validateEqualCurrency(final Money value1, final Money value2) {
		return value1.getCurrency().equals(value2.getCurrency());
	}
	public boolean validateNoDrafModeInvoices(final int id) {
		Collection<Invoice> draftInvoices = this.repository.findInvoicesNotPublishedBySponsorshipId(id);
		return draftInvoices.isEmpty();
	}
	public boolean validateExistsPublishedInvoices(final int id) {
		Collection<Invoice> pInvoices = this.repository.findPublishedInvoicesBySponsorshipId(id);
		return !pInvoices.isEmpty();
	}
	public boolean validateInvoicesAmount(final Money value, final int id) {
		//Validate here that the sum of the total amount of all their invoices must be equal to the amount of the sponsorship.
		Double ac = .0;
		Collection<Invoice> pInvoices = this.repository.findPublishedInvoicesBySponsorshipId(id);
		if (!pInvoices.isEmpty())
			for (final Invoice i : pInvoices)
				ac += i.totalAmount().getAmount();

		return ac.equals(value.getAmount());
	}
	public boolean validateSponsorshipCurrency(final Money value, final Sponsorship sp) {
		return value.getCurrency().equals(sp.getAmount().getCurrency());
	}

	public boolean isSelectedProject(final Sponsorship object, final Project project) {
		return object.getProject() != null && object.getProject().getId() == project.getId();
	}

	public boolean isSelectedProject(final TrainingModule object, final Project project) {
		return object.getProject() != null && object.getProject().getId() == project.getId();
	}

	public boolean isSelectedTrainingModule(final TrainingSession object, final TrainingModule tm) {
		return object.getTrainingModule() != null && object.getTrainingModule().getId() == tm.getId();
	}

	public boolean validateExistsPublishedTrainingSessions(final int id) {
		Collection<TrainingSession> pTrainingSessions = this.repository.findTrainingSessionsNotPublishedByTrainingModuleId(id);

		return pTrainingSessions.isEmpty();
	}
	public boolean validatePublishedInvoicesAmount(final Sponsorship sp, final double quantity, final double tax) {
		Collection<Invoice> pInvoices = this.repository.findPublishedInvoicesBySponsorshipId(sp.getId());
		double total = quantity + tax;
		if (!pInvoices.isEmpty())
			for (final Invoice i : pInvoices)
				total += i.getQuantity().getAmount() + i.getTax().getAmount();
		return sp.getAmount().getAmount() >= total;
	}
}
