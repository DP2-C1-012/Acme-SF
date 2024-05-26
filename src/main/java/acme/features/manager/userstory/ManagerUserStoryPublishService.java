
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.ValidatorService;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	@Autowired
	protected ManagerUserStoryRepository	repository;

	@Autowired
	protected ValidatorService				service;


	@Override
	public void authorise() {
		UserStory object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findUserStorieById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.isDraftMode() && object.getManager().getUserAccount().getId() == userAccountId);
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
		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimatedCost")) {
			super.state(this.service.validateMoneyQuantity(object.getEstimatedCost()), "estimatedCost", "manager.user-story.form.error.cost");
			super.state(this.service.validateMoneyCurrency(object.getEstimatedCost()), "estimatedCost", "manager.user-story.form.error.currency");
			super.state(this.service.validateCurrencyChange(object.getEstimatedCost(), object.getId()), "estimatedCost", "manager.user-story.form.error.currency");

		}
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
