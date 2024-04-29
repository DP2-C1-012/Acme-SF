
package acme.features.manager.projectUs;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.projectus.ProjectUs;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUsCreateService extends AbstractService<Manager, ProjectUs> {

	@Autowired
	protected ManagerProjectUsRepository repository;


	@Override
	public void authorise() {
		UserStory object;
		int id;
		id = super.getRequest().getData("userStoryId", int.class);
		object = this.repository.findUserStorieById(id);
		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getManager().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		ProjectUs object;
		object = new ProjectUs();
		final UserStory userstory;
		int userstoryId;
		userstoryId = super.getRequest().getData("userStoryId", int.class);
		userstory = this.repository.findUserStorieById(userstoryId);
		object.setUserStory(userstory);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUs object) {
		assert object != null;
		int projectId;
		Project project;
		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findProjectById(projectId);
		super.bind(object, "id");
		object.setProject(project);
	}

	@Override
	public void validate(final ProjectUs object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("userStory") && !super.getBuffer().getErrors().hasErrors("project")) {
			Collection<UserStory> userStories = this.repository.findUserStoriesByProjectId(object.getProject().getId());
			super.state(!userStories.contains(object.getUserStory()), "project", "manager.projectUserStory.error.containsUs");
		}
	}

	@Override
	public void perform(final ProjectUs object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUs object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "userStory", "project");
		int userstoryId = super.getRequest().getData("userStoryId", int.class);
		dataset.put("userStoryId", super.getRequest().getData("userStoryId", int.class));
		Manager manager = this.repository.findManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		Collection<Project> projects;
		projects = this.repository.findDraftModeProjectsByManager(manager);
		UserStory userstory = this.repository.findUserStorieById(userstoryId);
		dataset.put("draftMode", userstory.isDraftMode());

		SelectChoices choices = new SelectChoices();

		if (object.getProject() == null)
			choices.add("0", "---", true);
		else
			choices.add("0", "---", false);

		for (final Project c : projects)
			if (object.getProject() != null && object.getProject().getId() == c.getId())
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
			else
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);

		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
