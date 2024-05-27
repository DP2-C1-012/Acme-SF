
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.ValidatorService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	protected SponsorInvoiceRepository	repository;

	@Autowired
	protected ValidatorService			validator;

	// AbstractService interface -------------------------------------

	private String						id			= "id";
	private String						code		= "code";
	private String						quantity	= "quantity";
	private String						tax			= "tax";
	private String						link		= "link";
	private String						dueDate		= "dueDate";
	private String						sponsorship	= "sponsorship";
	private String						draftMode	= "draftMode";


	@Override
	public void authorise() {
		Invoice object;
		int invId;
		invId = super.getRequest().getData(this.id, int.class);
		object = this.repository.findInvoiceById(invId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsorship().getSponsor().getUserAccount().getId() == userId && object.isDraftMode());
	}

	@Override
	public void load() {
		Invoice object;
		int invId;
		invId = super.getRequest().getData(this.id, int.class);
		object = this.repository.findInvoiceById(invId);
		Sponsorship invSponsorship = this.repository.findSponsorshipByInvId(invId);
		object.setSponsorship(invSponsorship);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, this.code, this.dueDate, this.quantity, this.tax, this.link);
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
		if (!super.getBuffer().getErrors().hasErrors(this.tax)) {
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
		Money emptyAmount = new Money();
		emptyAmount.setAmount(.0);
		emptyAmount.setCurrency("EUR");
		dataset = super.unbind(object, this.code, this.dueDate, this.quantity, this.tax, this.link, this.draftMode, this.sponsorship);
		if (object.getQuantity() == null || object.getTax() == null)
			dataset.put("totalAmount", emptyAmount);
		else
			dataset.put("totalAmount", object.totalAmount());
		super.getResponse().addData(dataset);
	}
}
