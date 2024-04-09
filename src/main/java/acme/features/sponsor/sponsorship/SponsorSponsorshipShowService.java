
package acme.features.sponsor.sponsorship;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		masterId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findOneSponsorshipById(masterId);
		sponsor = sponsorship.getSponsor();
		status = super.getRequest().getPrincipal().getAccountId() == sponsor.getUserAccount().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;

		Date start = object.getStartDate();
		Date end = object.getEndDate();
		final Duration duration = MomentHelper.computeDuration(start, end);

		long days = duration.toDaysPart();
		long hours = duration.toHoursPart();
		long minutes = duration.toMinutesPart();
		StringBuilder sb = new StringBuilder();

		if (days > 0)
			sb.append(days).append("d ");
		if (hours > 0)
			sb.append(hours).append("h ");
		sb.append(minutes).append("m");
		sb.toString();

		choices = SelectChoices.from(SponsorshipType.class, object.getType());
		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "contact", "link", "draftMode", "sponsor", "project");
		dataset.put("types", choices);
		dataset.put("duration", sb);
		super.getResponse().addData(dataset);
	}
}
