
package acme.features.authenticated.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecord.Mark;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.codeAudit.Type;
import acme.entities.projects.Project;

@Service
public class AuthenticatedCodeAuditShowService extends AbstractService<Authenticated, CodeAudit> {

	@Autowired
	protected AuthenticatedCodeAuditRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		CodeAudit ca = this.repository.findOneCodeAuditByID(id);
		super.getResponse().setAuthorised(ca.isPublished());
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		CodeAudit ca = this.repository.findOneCodeAuditByID(id);
		super.getBuffer().addData(ca);
	}

	@Override
	public void unbind(final CodeAudit object) {
		SelectChoices choicesMark;
		SelectChoices choicesType;
		SelectChoices choicesProject;
		Dataset data;
		Collection<Project> projects;
		data = super.unbind(object, "code", "executionDate", "correctiveActions", "optionalLink", "published");
		projects = this.repository.findAllPublishedProjects();
		choicesMark = SelectChoices.from(Mark.class, object.getMark());
		choicesType = SelectChoices.from(Type.class, object.getType());
		choicesProject = SelectChoices.from(projects, "code", object.getProject());
		data.put("project", choicesProject.getSelected().getKey());
		data.put("projects", choicesProject);
		data.put("type", choicesType.getSelected().getKey());
		data.put("mark", choicesMark.getSelected().getKey());
		data.put("types", choicesType);
		data.put("marks", choicesMark);
		super.getResponse().addData(data);
	}
}
