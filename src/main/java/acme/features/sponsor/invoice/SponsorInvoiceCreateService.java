
package acme.features.sponsor.invoice;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Invoice object;
		int sponsorshipId;
		Sponsorship sponsorship;
		sponsorshipId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", sponsorshipId);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		object = new Invoice();
		object.setDraftMode(true);
		object.setSponsorship(sponsorship);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(this.validateMoneyQuantity(object.getQuantity()), "quantity", "sponsor.invoice.form.error.quantity");
		if (!super.getBuffer().getErrors().hasErrors("tax"))
			super.state(this.validateMoneyQuantity(object.getQuantity()), "tax", "sponsor.invoice.form.error.tax");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;
			existing = this.repository.findInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.invoice.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("registrationTime")) {
			super.state(this.validateFuture(object.getRegistrationTime()), "registrationTime", "sponsor.invoice.form.error.registration-time-future");
			super.state(this.validateDate(object.getRegistrationTime()), "registrationTime", "sponsor.invoice.form.error.registration-time-date");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			super.state(this.validateDate(object.getDueDate()), "dueDate", "sponsor.invoice.form.error.due-date-date");
			if (object.getRegistrationTime() != null)
				super.state(this.validateMoment(object.getRegistrationTime(), object.getDueDate()), "dueDate", "sponsor.invoice.form.error.due-date-month");
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode", "sponsorship");
		super.getResponse().addData(dataset);
	}

	public boolean validateMoneyQuantity(final Money value) {
		return value.getAmount() >= 0 && value.getAmount() <= 1000000;
	}
	public boolean validateMoneyCurrency(final Money value) {
		//Validate here currency
		return true;
	}
	public boolean validateDate(final Date value) {
		//Validate here date
		return true;
	}
	public boolean validateMoment(final Date startDate, final Date endDate) {
		//Validate here moment 1 month at least
		Date minimum = MomentHelper.deltaFromMoment(startDate, 30, ChronoUnit.DAYS);
		return MomentHelper.isAfterOrEqual(endDate, minimum);
	}
	public boolean validateFuture(final Date registration) {
		//Validate here moment is future
		return MomentHelper.isBefore(registration, MomentHelper.getCurrentMoment());
	}
}
