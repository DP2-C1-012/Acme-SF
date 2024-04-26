
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.services.AbstractService;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		UserStory object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStorieById(id);
		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		UserStory object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStorieById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;
		super.bind(object, "title", "description", "estimatedCostPerHour", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedCost"))
			super.state(object.getEstimatedCost().getAmount() >= 0, "estimatedCost", "manager.userstory.form.error.estimatedCost");
		if (!super.getBuffer().getErrors().hasErrors("title"))
			super.state(object.getTitle().length() >= 0, "title", "manager.userstory.form.error.title");
		if (!super.getBuffer().getErrors().hasErrors("description"))
			super.state(object.getDescription().length() >= 0, "description", "manager.userstory.form.error.description");
		if (!super.getBuffer().getErrors().hasErrors("acceptanceCriteria"))
			super.state(object.getAcceptanceCriteria().length() >= 0, "acceptanceCriteria", "manager.userstory.form.error.acceptanceCriteria");
	}

	@Override
	public void perform(final UserStory object) {
		object.setDraftMode(false);
		this.repository.save(object);
	}

}
