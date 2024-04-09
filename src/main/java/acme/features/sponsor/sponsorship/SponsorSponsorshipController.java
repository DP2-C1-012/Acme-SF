
package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipShowService		showService;

	//	@Autowired
	//	private EmployerJobCreateService	createService;
	//
	//	@Autowired
	//	private EmployerJobUpdateService	updateService;
	//
	//	@Autowired
	//	private EmployerJobDeleteService	deleteService;

	@Autowired
	private SponsorSponsorshipListAllService	listAllService;

	//	@Autowired
	//	private EmployerJobListMineService	listMineService;
	//
	//	@Autowired
	//	private EmployerJobPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
	}
}
