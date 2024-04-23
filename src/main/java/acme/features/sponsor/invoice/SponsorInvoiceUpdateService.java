
package acme.features.sponsor.invoice;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	protected SponsorInvoiceRepository repository;

	// AbstractService interface -------------------------------------


	@Override
	public void authorise() {
		Invoice object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findInvoiceById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsorship().getSponsor().getUserAccount().getId() == userId && object.isDraftMode());
	}

	@Override
	public void load() {
		Invoice object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findInvoiceById(id);
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
		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(this.validateMoneyQuantity(object.getQuantity()), "quantity", "sponsor.invoice.form.error.amount");
			super.state(this.validateMoneyQuantity(object.getQuantity()), "quantity", "sponsor.invoice.form.error.currency");
		}
		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			super.state(this.validateMoneyQuantity(object.getTax()), "tax", "sponsor.invoice.form.error.amount");
			super.state(this.validateMoneyQuantity(object.getTax()), "tax", "sponsor.invoice.form.error.currency");
			if (!super.getBuffer().getErrors().hasErrors("quantity"))
				super.state(this.validateEqualCurrency(object.getTax(), object.getQuantity()), "tax", "sponsor.invoice.form.error.same-currency");
		}
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice inv;
			inv = this.repository.findInvoiceByCode(object.getCode());
			final Invoice inv2 = object.getCode().equals("") || object.getCode() == null ? null : this.repository.findInvoiceById(object.getId());
			if (inv2 != null)
				super.state(inv2.equals(inv), "code", "sponsor.invoice.form.error.code");
			else
				super.state(inv == null, "code", "sponsor.invoice.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("registrationTime")) {
			super.state(this.validateFuture(object.getRegistrationTime()), "registrationTime", "sponsor.invoice.form.error.registration-time-past");
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
		dataset.put("totalAmount", object.totalAmount());
		super.getResponse().addData(dataset);
	}

	public boolean validateMoneyQuantity(final Money value) {
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
	public boolean validateEqualCurrency(final Money value1, final Money value2) {
		return value1.getCurrency().equals(value2.getCurrency());
	}
	public boolean validateFuture(final Date registration) {
		//Validate here moment is past
		return MomentHelper.isBefore(registration, MomentHelper.getCurrentMoment());
	}

}
