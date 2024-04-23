
package acme.componenents;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.client.helpers.MomentHelper;
import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerAdvisor {

	@Autowired
	protected BannerAdvisorRepository repository;


	@ModelAttribute("banner")
	public Banner getBanner() {
		Banner res;
		try {
			Date current = MomentHelper.getCurrentMoment();
			Collection<Banner> all = this.repository.findAllBanners();
			res = all.stream().filter(b -> (b.getDisplayPeriodStart().after(current) || b.getDisplayPeriodStart().equals(current) && (b.getDisplayPeriodEnd().before(current) || b.getDisplayPeriodEnd().equals(current)))).findAny().get();
		} catch (final Throwable oops) {
			oops.printStackTrace();
			res = null;
		}
		return res;
	}

}
