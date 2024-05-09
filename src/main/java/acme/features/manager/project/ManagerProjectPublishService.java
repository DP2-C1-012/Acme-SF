
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	protected ManagerProjectRepository repository;


	@Override
	public void authorise() {
		Project object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);
		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Project project;
		int id;

		id = super.getRequest().getData("id", int.class);
		project = this.repository.findProjectById(id);

		super.getBuffer().addData(project);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "id", "code", "title", "abstrac", "cost", "link", "indication");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		final Collection<UserStory> us = this.repository.findUserStoriesByProjectId(object.getId());
		super.state(!us.isEmpty(), "*", "manager.project.form.error.nous");
		if (!us.isEmpty()) {
			boolean usPublished;
			usPublished = us.stream().allMatch(x -> !x.isDraftMode());
			super.state(usPublished, "*", "manager.project.form.error.nouspub");
		}
		super.state(!object.isIndication(), "indication", "manager.project.form.error.critical");
		if (!super.getBuffer().getErrors().hasErrors("cost"))
			super.state(object.getCost().getAmount() >= 0, "cost", "manager.project.form.error.cost");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project exist;
			exist = this.repository.findProjectByCode(object.getCode());
			final Project p = object.getCode().equals("") || object.getCode().equals(null) ? null : this.repository.findProjectById(object.getId());
			super.state(p.equals(exist) || exist == null, "code", "manager.project.form.error.code");
		}

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstrac", "cost", "link", "draftMode", "manager", "indication");
		super.getResponse().addData(dataset);
	}

}
