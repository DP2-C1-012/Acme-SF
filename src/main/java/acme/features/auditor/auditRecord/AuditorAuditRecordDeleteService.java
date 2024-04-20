
package acme.features.auditor.auditRecord;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

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
		super.getResponse().setAuthorised(auditRecord.getCodeAudit().getAuditor().getId() == auditorID && !auditRecord.isPublished());
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
	public void bind(final AuditRecord object) {
		super.bind(object, "code", "periodStart", "periodEnd", "mark", "optionalLink", "codeAudit");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
	}

	@Override
	public void perform(final AuditRecord object) {
		CodeAudit codeAudit;
		Collection<AuditRecord> auditRecords;
		this.repository.delete(object);
		codeAudit = this.repository.findOneCodeAuditByID(object.getCodeAudit().getId());
		auditRecords = this.repository.findAuditRecordsByCodeAuditID(object.getCodeAudit().getId());
		Optional<Entry<Mark, Long>> entry = auditRecords.stream().map(ar -> ar.getMark()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().max(Comparator.comparing(Map.Entry::getValue));
		codeAudit.setMark(entry.isPresent() ? entry.get().getKey() : Mark.F_MINUS);
		this.repository.save(codeAudit);
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
