
package acme.features.any.claim;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.ValidatorService;
import acme.entities.claim.Claim;

@Service
public class AnyClaimPublishService extends AbstractService<Any, Claim> {

	@Autowired
	protected AnyClaimRepository	repository;

	@Autowired
	protected ValidatorService		validator;

	// AbstractService interface ----------------------------------------------

	private String					code				= "code";
	private String					instantiationMoment	= "instantiationMoment";
	private String					heading				= "heading";
	private String					description			= "description";
	private String					department			= "department";
	private String					email				= "email";
	private String					link				= "link";
	private String					confirmation		= "confirmation";


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim object;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object = new Claim();
		object.setInstantiationMoment(moment);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim object) {
		assert object != null;
		super.bind(object, this.code, this.instantiationMoment, this.heading, this.description, this.department, this.email, this.link, this.confirmation);
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors(this.code)) {
			Claim c;
			c = this.repository.findOneClaimByCode(object.getCode());
			super.state(c == null, this.code, "any.claim.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors(this.instantiationMoment))
			super.state(this.validator.validateDate(object.getInstantiationMoment()), this.instantiationMoment, "any.claim.form.error.instantiationMoment");

		super.state(object.isConfirmation(), this.confirmation, "any.claim.form.error.confirmation");

	}

	@Override
	public void perform(final Claim object) {
		assert object != null;
		this.repository.save(object);

	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, this.code, this.instantiationMoment, this.heading, this.description, this.department, this.email, this.link, this.confirmation);
		super.getResponse().addData(dataset);
	}

}
