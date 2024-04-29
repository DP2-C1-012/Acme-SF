
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListAllService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.getUserStoryByManager(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost");

		super.getResponse().addGlobal("showCreate", false);

		super.getResponse().addData(dataset);
	}

}
