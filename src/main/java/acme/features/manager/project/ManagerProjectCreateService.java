
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.components.ValidatorService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	@Autowired
	protected ManagerProjectRepository	repository;

	@Autowired
	protected ValidatorService			service;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Project object;
		object = new Project();
		final Manager manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setManager(manager);
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "abstrac", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			super.state(object.getCost().getAmount() >= 0, "cost", "manager.project.form.error.cost");
			super.state(this.service.validateMoneyCurrency(object.getCost()), "cost", "manager.project.form.error.currency");
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project exist;
			exist = this.repository.findProjectByCode(object.getCode());
			super.state(exist == null, "code", "manager.project.form.error.code");
		}

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstrac", "cost", "link", "draftMode", "manager");
		super.getResponse().addData(dataset);
	}
}
