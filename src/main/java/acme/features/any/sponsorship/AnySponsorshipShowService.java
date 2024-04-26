
package acme.features.any.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	@Autowired
	protected AnySponsorshipRepository	repository;

	// AbstractService interface ----------------------------------------------

	private String						id	= "id";


	@Override
	public void authorise() {
		Sponsorship object;
		int spId;
		spId = super.getRequest().getData(this.id, int.class);
		object = this.repository.findOneSponsorshipById(spId);
		super.getResponse().setAuthorised(!object.isDraftMode());
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
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "link", "contact");
		super.getResponse().addData(dataset);
	}
}
