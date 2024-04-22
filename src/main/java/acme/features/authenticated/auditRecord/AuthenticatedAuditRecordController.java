
package acme.features.authenticated.auditRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.auditRecord.AuditRecord;

@Controller
public class AuthenticatedAuditRecordController extends AbstractController<Authenticated, AuditRecord> {

	@Autowired
	protected AuthenticatedAuditRecordListOfCodeAuditService	listOfCodeAudit;

	@Autowired
	protected AuthenticatedAuditRecordShowService				showService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-of-code-audit", "list", this.listOfCodeAudit);
		super.addBasicCommand("show", this.showService);
	}
}
