
package acme.features.sponsor.sponsorship;

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
		final Date actualDate = MomentHelper.getCurrentMoment();
		object.setMoment(actualDate);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(SponsorSponsorshipCreateService.validateMoneyQuantity(object.getAmount()), "amount", "sponsor.sponsorship.form.error.amount");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;
			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "sponsor.sponsorship.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("amount"))
			super.state(SponsorSponsorshipCreateService.validateMoneyCurrency(object.getAmount()), "amount", "sponsor.sponsorship.form.error.amount");
		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			super.state(MomentHelper.isAfterOrEqual(object.getStartDate(), MomentHelper.getCurrentMoment()), "startPeriod", "sponsor.sponsorship.form.error.start-period");
			super.state(SponsorSponsorshipCreateService.validateDate(object.getStartDate()), "startPeriod", "sponsor.sponsorship.form.error.dates");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Date minimumEndDate;
			super.state(SponsorSponsorshipCreateService.validateDate(object.getEndDate()), "endPeriod", "sponsor.sponsorship.form.error.dates");
			if (object.getStartDate() != null) {
				minimumEndDate = MomentHelper.deltaFromMoment(object.getStartDate(), 30, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfterOrEqual(object.getEndDate(), minimumEndDate), "endPeriod", "sponsor.sponsorship.form.error.end-period");

			}
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
		dataset = super.unbind(object, "code", "amount", "moment", "startPeriod", "endPeriod", "type", "email", "link", "draftMode", "sponsor");
		final SelectChoices choices = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();

		for (final Project c : projects)
			if (object.getProject() != null && object.getProject().getId() == c.getId())
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), true);
			else
				choices.add(Integer.toString(c.getId()), c.getCode() + "-" + c.getTitle(), false);

		dataset.put("projects", choices);
		SelectChoices types = SelectChoices.from(SponsorshipType.class, object.getType());
		dataset.put("types", types);
		super.getResponse().addData(dataset);
	}

	public static boolean validateMoneyQuantity(final Money value) {
		//Validate here quantity 0 y 100000
		return true;
	}
	public static boolean validateMoneyCurrency(final Money value) {
		//Validate here currency
		return true;
	}
	public static boolean validateDate(final Date value) {
		//Validate here 
		return true;
	}
}
