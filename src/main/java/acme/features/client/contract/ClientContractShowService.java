
package acme.features.client.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientContractShowService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository repository;


	@Override
	public void authorise() {
		Contract contract;
		int id;
		boolean status;

		id = super.getRequest().getData("id", int.class);
		contract = this.repository.getContractById(id);
		final Principal p = super.getRequest().getPrincipal();
		final int clientId = p.getAccountId();
		status = contract.getClient().getUserAccount().getId() == clientId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		int id;

		id = super.getRequest().getData("id", int.class);
		contract = this.repository.getContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Dataset dataset;
		final List<ProgressLogs> progressLogs = (List<ProgressLogs>) this.repository.getProgressLogsByContractId(object.getId());
		dataset = super.unbind(object, "code", "moment", "provider", "customer", "goals", "budget", "project", "draftMode");
		dataset.put("projectCode", object.getProject().getCode());
		dataset.put("hasPL", !progressLogs.isEmpty());
		dataset.put("money", object.getBudget());
		super.getResponse().addData(dataset);
	}
}
