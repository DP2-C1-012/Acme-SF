
package acme.features.client.contract;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	ClientContractRepository clientContractRepository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Contract contract;
		Client client;

		masterId = super.getRequest().getData("id", int.class);
		contract = this.clientContractRepository.findOneContractById(masterId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		int id;

		id = super.getRequest().getData("id", int.class);

		contract = this.clientContractRepository.findOneContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.clientContractRepository.findOneProjectById(projectId);

		super.bind(object, "code", "providerName", "customerName", "goals", "budget");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.clientContractRepository.findOneContractByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "client.contract.form.error.duplicated");

			if (!super.getBuffer().getErrors().hasErrors("budget")) {
				if (object.getProject() != null) {
					super.state(object.getBudget().getCurrency().equals(object.getProject().getCost().getCurrency()), "budget", "client.contract.form.error.different-currency");
					Collection<Project> publishedProjects = this.clientContractRepository.findAllProjectsPublished();
					boolean projectExists = publishedProjects.stream().anyMatch(p -> p.getId() == object.getProject().getId());
					if (!projectExists)
						throw new IllegalStateException("It is not possible to create a contract with an unpublished project.");
				}
				super.state(this.checkContractsAmountsLessThanProjectCost(object), "budget", "client.contract.form.error.excededBudget");
				super.state(object.getBudget().getAmount() > 0, "budget", "client.contract.form.error.negative-amount");

				List<SystemConfiguration> sc = this.clientContractRepository.findSystemConfiguration();
				final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getBudget().getCurrency()));

				super.state(foundCurrency, "budget", "client.contract.form.error.currency-not-suported");

			}
		}

	}

	private Boolean checkContractsAmountsLessThanProjectCost(final Contract object) {
		assert object != null;

		if (object.getProject() != null) {
			Collection<Contract> contratos = this.clientContractRepository.findManyContractByProjectId(object.getProject().getId());

			Double budgetTotal = contratos.stream().filter(contract -> !contract.isDraftMode()).mapToDouble(contract -> contract.getBudget().getAmount()).sum();

			Double projectCost = object.getProject().getCost().getAmount();

			return projectCost >= budgetTotal + object.getBudget().getAmount();
		}

		return true;
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;
		object.setDraftMode(false);
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
