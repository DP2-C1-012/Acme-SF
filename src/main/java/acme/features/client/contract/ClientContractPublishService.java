
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository repository;


	@Override
	public void authorise() {
		Contract contract;
		int id;
		id = super.getRequest().getData("id", int.class);
		contract = this.repository.getContractById(id);
		final Principal p = super.getRequest().getPrincipal();
		final int userAccountId = p.getAccountId();
		super.getResponse().setAuthorised(contract.getClient().getUserAccount().getId() == userAccountId && contract.getDraftMode());
	}

	@Override
	public void load() {
		Contract object;
		object = new Contract();
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		Contract object2;
		int id;

		id = super.getRequest().getData("id", int.class);
		object2 = this.repository.getContractById(id);
		object.setProject(object2.getProject());
		object.setClient(object2.getClient());
		super.bind(object, "code", "provider", "moment", "customer", "goals", "budget");
	}

	@Override
	public void validate(final Contract object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		final Collection<Contract> contracts = this.repository.getContractsFromProjectId(object.getProject().getId());
		super.state(!contracts.isEmpty(), "*", "manager.project.form.error.noContracts");
		if (!contracts.isEmpty()) {
			boolean overBudget = true;
			double totalBudget = 0.0;
			for (Contract c : contracts)
				totalBudget = totalBudget + c.getBudget().getAmount();
			if (totalBudget < object.getProject().getCost().getAmount())
				overBudget = false;
			super.state(overBudget, "*", "manager.project.form.error.overBudget");
		}

		if (!super.getBuffer().getErrors().hasErrors("instantiationMoment"))
			super.state(MomentHelper.isBefore(object.getMoment(), MomentHelper.getCurrentMoment()), "instantiationMoment", "client.contract.form.error.moment");

	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "provider", "customer", "goals", "budget", "project", "client", "draftMode");
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}

}
