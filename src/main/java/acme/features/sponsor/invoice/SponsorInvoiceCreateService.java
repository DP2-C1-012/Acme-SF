
package acme.features.sponsor.invoice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.ValidatorService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorInvoiceRepository	repository;

	@Autowired
	protected ValidatorService			validator;

	// AbstractService interface ----------------------------------------------
	private String						masterId			= "masterId";
	private String						code				= "code";
	private String						quantity			= "quantity";
	private String						tax					= "tax";
	private String						link				= "link";
	private String						dueDate				= "dueDate";
	private String						registrationTime	= "registrationTime";


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Invoice object;
		int sponsorshipId;
		Sponsorship sponsorship;
		sponsorshipId = super.getRequest().getData(this.masterId, int.class);
		super.getResponse().addGlobal(this.masterId, sponsorshipId);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		object = new Invoice();
		object.setDraftMode(true);
		object.setSponsorship(sponsorship);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, this.code, this.dueDate, this.quantity, this.tax, this.link);
		final Date cMoment = MomentHelper.getCurrentMoment();
		object.setRegistrationTime(cMoment);
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors(this.code)) {
			Invoice inv = this.repository.findInvoiceByCode(object.getCode());
			if (inv != null)
				super.state(inv.getId() == object.getId(), this.code, "sponsor.invoice.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors(this.quantity)) {
			super.state(this.validator.validateMoneyQuantity(object.getQuantity()), this.quantity, "sponsor.invoice.form.error.amount");
			super.state(this.validator.validateMoneyCurrency(object.getQuantity()), this.quantity, "sponsor.invoice.form.error.currency");
		}
		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			super.state(this.validator.validateMoneyQuantity(object.getTax()), this.tax, "sponsor.invoice.form.error.amount");
			super.state(this.validator.validateMoneyCurrency(object.getTax()), this.tax, "sponsor.invoice.form.error.currency");
			if (!super.getBuffer().getErrors().hasErrors(this.quantity)) {
				super.state(this.validator.validateEqualCurrency(object.getTax(), object.getQuantity()), this.tax, "sponsor.invoice.form.error.same-currency");
				super.state(this.validator.validatePublishedInvoicesAmount(object.getSponsorship(), object.getQuantity().getAmount(), object.getTax().getAmount()), "*", "sponsor.invoice.form.error.published-invoices");
			}
		}
		if (!super.getBuffer().getErrors().hasErrors(this.dueDate)) {
			super.state(this.validator.validateDate(object.getDueDate()), this.dueDate, "sponsor.invoice.form.error.due-date-date");
			if (object.getRegistrationTime() != null)
				super.state(this.validator.validateMoment(object.getRegistrationTime(), object.getDueDate()), this.dueDate, "sponsor.invoice.form.error.due-date-month");
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
		dataset = super.unbind(object, this.code, this.registrationTime, this.dueDate, this.quantity, this.tax, this.link);
		super.getResponse().addData(dataset);
	}
}
