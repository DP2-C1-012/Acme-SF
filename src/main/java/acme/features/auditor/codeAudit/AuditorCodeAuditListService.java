
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> codeAudits;
		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();
		codeAudits = this.repository.FindCodeAuditsByAuditorID(id);
		super.getBuffer().addData(codeAudits);
	}

	@Override
	public void unbind(final CodeAudit object) {
		Dataset data;
		data = super.unbind(object, "code", "type", "mark", "project");
		super.getResponse().addData(data);
	}
}
