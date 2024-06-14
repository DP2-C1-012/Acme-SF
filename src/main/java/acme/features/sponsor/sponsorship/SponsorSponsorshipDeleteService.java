
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorSponsorshipRepository	repository;

	// AbstractService interface ----------------------------------------------

	private String							id	= "id";


	@Override
	public void authorise() {
		Sponsorship object;
		int spId;
		spId = super.getRequest().getData(this.id, int.class);
		object = this.repository.findOneSponsorshipById(spId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsor().getUserAccount().getId() == userId && object.isDraftMode());
	}

	@Override
	public void load() {
		Sponsorship object;
		int spId;

		spId = super.getRequest().getData(this.id, int.class);
		object = this.repository.findOneSponsorshipById(spId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		final Collection<Invoice> invoice = this.repository.findInvoicesBySponsorshipId(object.getId());
		for (final Invoice i : invoice)
			this.repository.delete(i);
		this.repository.delete(object);
	}
}
