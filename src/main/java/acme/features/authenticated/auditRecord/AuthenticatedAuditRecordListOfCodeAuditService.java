
package acme.features.authenticated.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.codeAudit.CodeAudit;

@Service
public class AuthenticatedAuditRecordListOfCodeAuditService extends AbstractService<Authenticated, AuditRecord> {

	@Autowired
	protected AuthenticatedAuditRecordRepository repository;


	@Override
	public void authorise() {
		int codeAuditID;
		CodeAudit codeAudit;
		codeAuditID = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditByID(codeAuditID);
		super.getResponse().setAuthorised(codeAudit.isPublished());
	}

	@Override
	public void load() {
		int codeAuditID;
		Collection<AuditRecord> auditRecords;
		codeAuditID = super.getRequest().getData("id", int.class);
		auditRecords = this.repository.findAuditRecordsByCodeAuditID(codeAuditID);
		super.getBuffer().addData(auditRecords);
	}

	@Override
	public void unbind(final AuditRecord object) {
		Dataset data;
		data = super.unbind(object, "code", "periodStart", "periodEnd");
		data.put("mark", object.getMark().toString());
		super.getResponse().addData(data);
	}
}
