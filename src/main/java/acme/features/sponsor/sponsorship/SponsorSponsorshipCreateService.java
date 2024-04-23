
package acme.features.sponsor.sponsorship;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		object = new Sponsorship();
		final Sponsor Sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setSponsor(Sponsor);
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, "code", "amount", "startDate", "endDate", "type", "contact", "link", "project");
		final Date moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship sp;
			sp = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(sp == null, "code", "sponsor.sponsorship.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			super.state(this.validateMoneyQuantity(object.getAmount()), "amount", "sponsor.sponsorship.form.error.amount");
			super.state(this.validateMoneyCurrency(object.getAmount()), "amount", "sponsor.sponsorship.form.error.currency");
		}
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			super.state(MomentHelper.isAfterOrEqual(object.getStartDate(), MomentHelper.getCurrentMoment()), "startDate", "sponsor.sponsorship.form.error.start-date");
			super.state(this.validateDate(object.getStartDate()), "startDate", "sponsor.sponsorship.form.error.date");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			super.state(this.validateDate(object.getEndDate()), "endDate", "sponsor.sponsorship.form.error.date");
			if (object.getStartDate() != null)
				super.state(this.validateMoment(object.getStartDate(), object.getEndDate()), "endDate", "sponsor.sponsorship.form.error.end-date");
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "amount", "moment", "startDate", "endDate", "type", "contact", "link", "draftMode", "sponsor", "project");
		final SelectChoices choices = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();
		for (final Project p : projects)
			if (object.getProject() != null && object.getProject().getId() == p.getId())
				choices.add(String.valueOf(p.getId()), p.getCode(), true);
			else
				choices.add(String.valueOf(p.getId()), p.getCode(), false);
		dataset.put("projects", choices);
		SelectChoices types = SelectChoices.from(SponsorshipType.class, object.getType());
		dataset.put("types", types);
		super.getResponse().addData(dataset);
	}

	public boolean validateMoneyQuantity(final Money value) {
		return value.getAmount() >= 0 && value.getAmount() <= 1000000;
	}
	public boolean validateMoneyCurrency(final Money value) {
		//Validate here currency
		String currencies = this.repository.findSystemConfigurationCurrencies();
		return currencies.contains(value.getCurrency());
	}
	public boolean validateDate(final Date value) {
		LocalDateTime maxDateTime = LocalDateTime.of(2100, Month.DECEMBER, 31, 23, 59);
		LocalDateTime minDateTime = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0);
		final Date max = Date.from(maxDateTime.atZone(ZoneId.systemDefault()).toInstant());
		final Date min = Date.from(minDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return MomentHelper.isAfterOrEqual(value, min) && MomentHelper.isBeforeOrEqual(value, max);
	}
	public boolean validateMoment(final Date startDate, final Date endDate) {
		//Validate here moment 1 month at least
		Date minimum = MomentHelper.deltaFromMoment(startDate, 30, ChronoUnit.DAYS);
		return MomentHelper.isAfterOrEqual(endDate, minimum);
	}
}
