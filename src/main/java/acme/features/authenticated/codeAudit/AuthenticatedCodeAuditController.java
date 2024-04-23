
package acme.features.authenticated.codeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.codeAudit.CodeAudit;

@Controller
public class AuthenticatedCodeAuditController extends AbstractController<Authenticated, CodeAudit> {

	@Autowired
	protected AuthenticatedCodeAuditShowService	showService;

	@Autowired
	protected AuthenticatedCodeAuditListService	listService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("list", this.listService);
	}

}
