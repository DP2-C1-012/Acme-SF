
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerDeleteService extends AbstractService<Administrator, Banner> {

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
	public void bind(final Banner object) {
		super.bind(object, "instantiationMomment", "displayPeriodStart", "displayPeriodEnd", "updateMomment", "slogan", "pictureURL", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
	}

	@Override
	public void perform(final Banner object) {
		object.setUpdateMomment(MomentHelper.getCurrentMoment());
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		Dataset data = super.unbind(object, "instantiationMomment", "displayPeriodStart", "displayPeriodEnd", "updateMomment", "slogan", "pictureURL", "link");
		super.getResponse().addData(data);
	}
}
