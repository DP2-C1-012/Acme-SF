
package acme.features.manager.userstory;

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
public class ManagerUserStoryListMineService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		Project object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findProjectById(masterId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findUserStoriesByProjectId(masterId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost");
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", masterId);
		dataset.put("masterId", masterId);
		final Project p = this.repository.findProjectById(masterId);
		final boolean showCreate = p.isDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<UserStory> object) {
		assert object != null;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", masterId);
		final Project c = this.repository.findProjectById(masterId);
		final boolean showCreate = c.isDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
