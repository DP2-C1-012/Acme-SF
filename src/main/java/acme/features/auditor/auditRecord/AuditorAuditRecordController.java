
package acme.features.auditor.auditRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditRecord.AuditRecord;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordController extends AbstractController<Auditor, AuditRecord> {

	@Autowired
	protected AuditorAuditRecordListOfCodeAuditService	listOfCodeAudit;

	@Autowired
	protected AuditorAuditRecordShowService				showService;

	@Autowired
	protected AuditorAuditRecordCreateService			createService;

	@Autowired
	protected AuditorAuditRecordDeleteService			deleteService;

	@Autowired
	protected AuditorAuditRecordUpdateService			updateService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-of-code-audit", "list", this.listOfCodeAudit);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}
}
