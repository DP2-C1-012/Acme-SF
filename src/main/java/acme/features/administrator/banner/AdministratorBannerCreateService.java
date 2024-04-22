
package acme.features.administrator.banner;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdministratorBannerRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner banner = new Banner();
		banner.setInstantiationMomment(MomentHelper.getCurrentMoment());
		banner.setUpdateMomment(MomentHelper.getCurrentMoment());
		super.getBuffer().addData(banner);
	}

	@Override
	public void bind(final Banner object) {
		super.bind(object, "displayPeriodStart", "displayPeriodEnd", "slogan", "pictureURL", "link");
	}

	@Override
	public void validate(final Banner object) {
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodStart")) {
			boolean state = object.getDisplayPeriodStart() != null;
			if (state) {
				long seconds = MomentHelper.computeDuration(object.getInstantiationMomment(), object.getDisplayPeriodStart()).abs().getSeconds();
				state = seconds >= 604800;
			}
			super.state(state, "displayPeriodStart", "administrator.banner.form.update.displayPeriodStart");
		}
		if (!super.getBuffer().getErrors().hasErrors("displayPeriodEnd")) {
			boolean state = object.getDisplayPeriodStart() != null && object.getDisplayPeriodEnd() != null && object.getDisplayPeriodStart().before(object.getDisplayPeriodEnd());
			super.state(state, "displayPeriodEnd", "administrator.banner.form.update.displayPeriodEnd");
		}
		if (!super.getBuffer().getErrors().hasErrors("pictureURL")) {
			boolean state = object.getPictureURL() != null;
			if (state)
				try {
					URI uri = new URI(object.getPictureURL());
					HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
					connection.setRequestMethod("GET");
					String contentType = connection.getContentType();
					state = false;
					if (contentType != null && contentType.startsWith("image"))
						state = true;
				} catch (Exception e) {
					state = false;
				}
			super.state(state, "pictureURL", "administrator.banner.form.update.pictureURL");
		}
	}

	@Override
	public void perform(final Banner object) {
		Date currentMoment = MomentHelper.getCurrentMoment();
		System.out.println(currentMoment);
		object.setUpdateMomment(currentMoment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		Dataset data = super.unbind(object, "instantiationMomment", "displayPeriodStart", "displayPeriodEnd", "updateMomment", "slogan", "pictureURL", "link");
		super.getResponse().addData(data);
	}
}
