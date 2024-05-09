
package acme.features.any.claim;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.claim.Claim;

@Controller
public class AnyClaimController extends AbstractController<Any, Claim> {

	@Autowired
	protected AnyClaimListService		listService;

	@Autowired
	protected AnyClaimShowService		showService;

	@Autowired
	protected AnyClaimPublishService	publishService;


	@PostConstruct
	void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("publish", "create", this.publishService);
	}

}
