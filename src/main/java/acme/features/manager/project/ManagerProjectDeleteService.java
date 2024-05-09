
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projectus.ProjectUs;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

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
		super.bind(object, "id", "code", "title", "abstrac", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		Collection<ProjectUs> projectUserStories = this.repository.findProjectUserStoriesByProject(object);
		for (final ProjectUs pus : projectUserStories)
			this.repository.delete(pus);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstrac", "link");
		super.getResponse().addData(dataset);
	}

}
