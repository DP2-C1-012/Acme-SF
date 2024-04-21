
package acme.features.authenticated.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuthenticatedAuditorCreateService extends AbstractService<Authenticated, Auditor> {

	@Autowired
	protected AuthenticatedAuditorRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Principal principal;
		int userAccountId;
		UserAccount userAccount;
		Auditor auditor;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		auditor = new Auditor();
		auditor.setUserAccount(userAccount);
		super.getBuffer().addData(auditor);
	}

	@Override
	public void bind(final Auditor object) {
		super.bind(object, "firm", "professionalID", "certifications", "moreInfo");
	}

	@Override
	public void validate(final Auditor object) {
		assert object != null;
	}

	@Override
	public void perform(final Auditor object) {
		this.repository.save(object);
	}

	@Override
	public void unbind(final Auditor object) {
		Dataset dataset;
		dataset = super.unbind(object, "firm", "professionalID", "certifications", "moreInfo");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
