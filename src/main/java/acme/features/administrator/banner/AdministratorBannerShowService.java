
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerShowService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner banner = this.repository.findOneBannerByID(super.getRequest().getData("id", int.class));
		super.getBuffer().addData(banner);
	}

	@Override
	public void unbind(final Banner object) {
		Dataset data = super.unbind(object, "instantiationMomment", "displayPeriodStart", "displayPeriodEnd", "updateMomment", "slogan", "pictureURL", "link");
		super.getResponse().addData(data);
	}
}
