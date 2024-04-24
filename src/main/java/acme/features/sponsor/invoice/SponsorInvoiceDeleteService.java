
package acme.features.sponsor.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceDeleteService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorInvoiceRepository	repository;

	// AbstractService interface ----------------------------------------------

	private String						id					= "id";
	private String						code				= "code";
	private String						quantity			= "quantity";
	private String						tax					= "tax";
	private String						link				= "link";
	private String						dueDate				= "dueDate";
	private String						registrationTime	= "registrationTime";
	private String						sponsorship			= "sponsorship";
	private String						draftMode			= "draftMode";


	@Override
	public void authorise() {
		Invoice object;
		int inv;
		inv = super.getRequest().getData(this.id, int.class);
		object = this.repository.findInvoiceById(inv);
		final Principal principal = super.getRequest().getPrincipal();
		final int userId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsorship().getSponsor().getUserAccount().getId() == userId && object.isDraftMode());
	}

	@Override
	public void load() {
		Invoice object;
		int inv;
		inv = super.getRequest().getData(this.id, int.class);
		object = this.repository.findInvoiceById(inv);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, this.code, this.registrationTime, this.dueDate, this.quantity, this.tax, this.link, this.draftMode, this.sponsorship);
		super.getResponse().addData(dataset);

	}
}
