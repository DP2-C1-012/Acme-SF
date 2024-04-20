
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.auditRecord.Mark;
import acme.entities.codeAudit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordShowService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {
		int auditRecordID;
		int auditorID;
		AuditRecord auditRecord;
		auditRecordID = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordByID(auditRecordID);
		auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setAuthorised(auditRecord.getCodeAudit().getAuditor().getId() == auditorID);
	}

	@Override
	public void load() {
		int auditRecordID;
		AuditRecord auditRecord;
		auditRecordID = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordByID(auditRecordID);
		super.getBuffer().addData(auditRecord);
	}

	@Override
	public void unbind(final AuditRecord object) {
		Dataset data;
		SelectChoices choicesCodeAudit;
		SelectChoices choicesMark;
		Collection<CodeAudit> codeAudits;
		int auditorID;
		auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		codeAudits = this.repository.findCodeAuditsOfAuditorByAuditorID(auditorID);
		data = super.unbind(object, "code", "periodStart", "periodEnd", "optionalLink", "published");
		choicesCodeAudit = SelectChoices.from(codeAudits, "code", object.getCodeAudit());
		data.put("codeAudits", choicesCodeAudit);
		data.put("codeAudit", choicesCodeAudit.getSelected().getKey());
		choicesMark = SelectChoices.from(Mark.class, object.getMark());
		data.put("marks", choicesMark);
		data.put("mark", choicesMark.getSelected().getKey());
		super.getResponse().addData(data);
	}
}
