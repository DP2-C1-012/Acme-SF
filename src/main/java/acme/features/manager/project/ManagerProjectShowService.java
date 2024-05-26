
package acme.features.manager.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {

	@Autowired
	protected ManagerProjectRepository repository;


	@Override
	public void authorise() {
		Project object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
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
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "id", "code", "title", "abstrac", "cost", "link", "draftMode", "manager");
		List<UserStory> userStories = this.repository.findUserStoriesByProjectId(object.getId()).stream().toList();
		dataset.put("hasUserStories", !userStories.isEmpty());
		super.getResponse().addData(dataset);
	}

}
