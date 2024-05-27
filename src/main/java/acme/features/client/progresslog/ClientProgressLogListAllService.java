
package acme.features.client.progresslog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogListAllService extends AbstractService<Client, ProgressLogs> {

	@Autowired
	protected ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<ProgressLogs> objects;
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		objects = this.repository.getProgressLogsByClientId(userAccountId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "recordId", "completeness", "responsible", "contract");
		dataset.put("contract", object.getContract().getCode());

		super.getResponse().addData(dataset);
	}
}
