
package acme.features.authenticated.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.auditRecord.Mark;
import acme.entities.codeAudit.CodeAudit;

@Service
public class AuthenticatedAuditRecordShowService extends AbstractService<Authenticated, AuditRecord> {

	@Autowired
	protected AuthenticatedAuditRecordRepository repository;


	@Override
	public void authorise() {
		AuditRecord auditRecord;
		int auditRecordID = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordByID(auditRecordID);

		super.getResponse().setAuthorised(auditRecord.isPublished());
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
		codeAudits = this.repository.findPublishedCodeAudits();
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
