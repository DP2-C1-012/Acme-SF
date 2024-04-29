
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		UserStory object;
		object = new UserStory();
		final Manager manager = this.repository.findOneManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setManager(manager);
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;
		super.bind(object, "id", "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
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
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());
		dataset = super.unbind(object, "id", "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode", "manager");
		dataset.put("priority", choices.getSelected().getKey());
		dataset.put("priorities", choices);
		super.getResponse().addData(dataset);
	}

}
