
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecord.Mark;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.codeAudit.Type;
import acme.entities.projects.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit codeAudit;
		Auditor auditor;
		codeAudit = new CodeAudit();
		auditor = this.repository.findAuditorByID(super.getRequest().getPrincipal().getActiveRoleId());
		codeAudit.setAuditor(auditor);
		super.getBuffer().addData(codeAudit);
	}

	@Override
	public void bind(final CodeAudit object) {
		super.bind(object, "code", "executionDate", "correctiveActions", "optionalLink", "type", "project");
	}

	@Override
	public void validate(final CodeAudit object) {
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;
			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null, "code", "auditor.codeAudit.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("executionDate")) {
			Date now;
			now = MomentHelper.getCurrentMoment();
			super.state(object.getExecutionDate() != null && object.getExecutionDate().before(now), "executionDate", "auditor.codeAudit.form.error.executionDate");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		this.repository.save(object);
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
