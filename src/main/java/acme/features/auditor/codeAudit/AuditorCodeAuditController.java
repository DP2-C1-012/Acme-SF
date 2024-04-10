
package acme.features.auditor.codeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.codeAudit.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditController extends AbstractController<Auditor, CodeAudit> {

	@Autowired
	protected AuditorCodeAuditListService		auditorCodeAuditListService;

	@Autowired
	protected AuditorCodeAuditShowService		auditorCodeAuditShowService;

	@Autowired
	protected AuditorCodeAuditCreateService		auditorCodeAuditCreateService;

	@Autowired
	protected AuditorCodeAuditUpdateService		auditorCodeAuditUpdateService;

	@Autowired
	protected AuditorCodeAuditDeleteService		auditorCodeAuditDeleteService;

	@Autowired
	protected AuditorCodeAuditPublishService	auditorCodeAuditPublishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.auditorCodeAuditListService);
		super.addBasicCommand("show", this.auditorCodeAuditShowService);
		super.addBasicCommand("create", this.auditorCodeAuditCreateService);
		super.addBasicCommand("update", this.auditorCodeAuditUpdateService);
		super.addBasicCommand("delete", this.auditorCodeAuditDeleteService);
		super.addCustomCommand("publish", "update", this.auditorCodeAuditPublishService);
	}
}
