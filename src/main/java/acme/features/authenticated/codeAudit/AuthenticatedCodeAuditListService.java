
package acme.features.authenticated.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.codeAudit.CodeAudit;

@Service
public class AuthenticatedCodeAuditListService extends AbstractService<Authenticated, CodeAudit> {

	@Autowired
	protected AuthenticatedCodeAuditRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> ca = this.repository.findPublishedCodeAudits();
		super.getBuffer().addData(ca);
	}

	@Override
	public void unbind(final CodeAudit object) {
		Dataset data;
		data = super.unbind(object, "code", "type");
		data.put("mark", object.getMark().toString());
		data.put("project", object.getProject().getCode());
		super.getResponse().addData(data);
	}
}
