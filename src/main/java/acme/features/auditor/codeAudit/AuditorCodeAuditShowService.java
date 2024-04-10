
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecord.Mark;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.codeAudit.Type;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		int auditorID;
		int codeAuditID;
		CodeAudit codeAudit;
		auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		codeAuditID = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditByID(codeAuditID);
		super.getResponse().setAuthorised(auditorID == codeAudit.getAuditor().getId());
	}

	@Override
	public void load() {
		CodeAudit codeAudit;
		int id;
		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditByID(id);
		super.getBuffer().addData(codeAudit);
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
