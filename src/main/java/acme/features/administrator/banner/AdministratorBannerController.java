
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.banner.Banner;

@Controller
public class AdministratorBannerController extends AbstractController<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerShowService	showService;
	@Autowired
	protected AdministratorBannerListService	listService;
	@Autowired
	protected AdministratorBannerCreateService	createService;
	@Autowired
	protected AdministratorBannerUpdateService	updateService;
	@Autowired
	protected AdministratorBannerDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		//super.addCustomCommand("publish", "update", this.auditorCodeAuditPublishService);
	}
}
