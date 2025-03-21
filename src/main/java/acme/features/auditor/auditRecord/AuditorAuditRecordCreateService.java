
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
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.auditRecord.Mark;
import acme.entities.codeAudit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord auditRecord;
		auditRecord = new AuditRecord();
		super.getBuffer().addData(auditRecord);
	}

	@Override
	public void bind(final AuditRecord object) {
		Mark mark;
		String markStr = super.getRequest().getData("mark", String.class);
		super.bind(object, "code", "periodStart", "periodEnd", "optionalLink", "codeAudit");
		//Soy demasiado vago para implementar un scanner
		switch (markStr) {
		case "A+":
			mark = Mark.A_PLUS;
			break;
		case "A":
			mark = Mark.A;
			break;
		case "B":
			mark = Mark.B;
			break;
		case "C":
			mark = Mark.C;
			break;
		case "F":
			mark = Mark.F;
			break;
		default:
			mark = Mark.F_MINUS;
			break;
		}
		object.setMark(mark);
	}

	@Override
	public void validate(final AuditRecord object) {
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord auditRecord = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(auditRecord == null || auditRecord.equals(object), "code", "auditor.auditRecord.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("periodStart"))
			super.state(object.getPeriodStart() != null && object.getCodeAudit() != null && object.getCodeAudit().getExecutionDate().before(object.getPeriodStart()), "periodStart", "auditor.auditRecord.form.error.periodStart");
		if (!super.getBuffer().getErrors().hasErrors("periodEnd")) {
			boolean state = object.getPeriodEnd() != null && object.getPeriodStart() != null && object.getPeriodEnd().after(object.getPeriodStart());
			if (state) {
				long seconds = MomentHelper.computeDuration(object.getPeriodStart(), object.getPeriodEnd()).abs().getSeconds();
				state = seconds >= 3600;
			}
			super.state(state, "periodEnd", "auditor.auditRecord.form.error.periodEnd.length");
		}
		if (!super.getBuffer().getErrors().hasErrors("codeAudit"))
			super.state(object.getCodeAudit() != null && !object.getCodeAudit().isPublished(), "codeAudit", "auditor.auditRecord.form.error.codeAudit.published");
	}

	@Override
	public void perform(final AuditRecord object) {
		CodeAudit codeAudit;
		Collection<AuditRecord> auditRecords;
		this.repository.save(object);
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
		codeAudits = this.repository.findNotPublishedCodeAuditsOfAuditorByAuditorID(auditorID);
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
