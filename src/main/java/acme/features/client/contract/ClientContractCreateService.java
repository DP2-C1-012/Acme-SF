
package acme.features.client.contract;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	@Autowired
	private ClientContractRepository clientContractRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Contract contract = new Contract();
		Client client;

		client = this.clientContractRepository.findOneClientById(super.getRequest().getPrincipal().getActiveRoleId());

		contract.setDraftMode(true);
		contract.setClient(client);

		super.getBuffer().addData(contract);

	}

	@Override
	public void bind(final Contract object) {

		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		object.setDraftMode(true);
		project = this.clientContractRepository.findOneProjectById(projectId);
		super.bind(object, "code", "providerName", "customerName", "goals", "budget");
		final Date cMoment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(cMoment);
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.clientContractRepository.findOneContractByCode(object.getCode());
			super.state(existing == null, "code", "client.contract.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			super.state(object.getBudget().getAmount() < 1000000, "budget", "client.contract.form.error.max-budget");
			super.state(object.getBudget().getAmount() > 0, "budget", "client.contract.form.error.negative-amount");
			if (object.getProject() != null) {
				super.state(object.getBudget().getCurrency().equals(object.getProject().getCost().getCurrency()), "budget", "client.contract.form.error.different-currency");
				Collection<Project> publishedProjects = this.clientContractRepository.findAllProjectsPublished();
				boolean projectExists = publishedProjects.stream().anyMatch(p -> p.getId() == object.getProject().getId());
				if (!projectExists)
					throw new IllegalStateException("It is not possible to create a contract with an unpublished project.");
			}
			List<SystemConfiguration> sc = this.clientContractRepository.findSystemConfiguration();
			final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getBudget().getCurrency()));

			super.state(foundCurrency, "budget", "client.contract.form.error.currency-not-suported");
		}
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		this.clientContractRepository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;

		projects = this.clientContractRepository.findAllProjectsPublished();
		choices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset;

		dataset = super.unbind(object, "code", "providerName", "customerName", "goals", "budget");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
