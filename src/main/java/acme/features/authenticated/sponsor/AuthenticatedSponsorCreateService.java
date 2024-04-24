
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Sponsor;

@Service
public class AuthenticatedSponsorCreateService extends AbstractService<Authenticated, Sponsor> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedSponsorRepository	repository;

	// AbstractService interface ----------------------------------------------

	private String								name		= "name";
	private String								benefits	= "benefits";
	private String								webPage		= "webPage";
	private String								email		= "email";


	@Override
	public void authorise() {
		boolean status;
		status = !super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsor object;
		Principal principal;
		int userId;
		UserAccount userAccount;
		principal = super.getRequest().getPrincipal();
		userId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userId);

		object = new Sponsor();
		object.setUserAccount(userAccount);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsor object) {
		assert object != null;
		super.bind(object, this.name, this.benefits, this.webPage, this.email);
	}

	@Override
	public void validate(final Sponsor object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsor object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsor object) {
		assert object != null;
		final Dataset dataset;
		dataset = super.unbind(object, this.name, this.benefits, this.webPage, this.email);
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
