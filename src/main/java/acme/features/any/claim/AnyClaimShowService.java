
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimShowService extends AbstractService<Any, Claim> {

	@Autowired
	protected AnyClaimRepository	repository;

	// AbstractService interface ----------------------------------------------

	private String					id	= "id";


	@Override
	public void authorise() {
		boolean status;
		int claimId;
		Claim claim;

		claimId = super.getRequest().getData(this.id, int.class);
		claim = this.repository.findOneClaimById(claimId);
		status = claim != null;

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Claim object;
		int claimId;
		claimId = super.getRequest().getData(this.id, int.class);
		object = this.repository.findOneClaimById(claimId);
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "email", "link");
		super.getResponse().addData(dataset);
	}

}
