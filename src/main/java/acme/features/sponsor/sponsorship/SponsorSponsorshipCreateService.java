
package acme.features.sponsor.sponsorship;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.components.ValidatorService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorSponsorshipRepository	repository;

	@Autowired
	protected ValidatorService				validator;

	// AbstractService interface ----------------------------------------------

	private String							code		= "code";
	private String							amount		= "amount";
	private String							startDate	= "startDate";
	private String							endDate		= "endDate";
	private String							type		= "type";
	private String							contact		= "contact";
	private String							link		= "link";
	private String							project		= "project";
	private String							sponsor		= "sponsor";
	private String							moment		= "moment";
	private String							draftMode	= "draftMode";


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		object = new Sponsorship();
		final Sponsor sp = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setSponsor(sp);
		object.setDraftMode(true);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, this.code, this.amount, this.startDate, this.endDate, this.type, this.contact, this.link, this.project);
		final Date cMoment = MomentHelper.getCurrentMoment();
		object.setMoment(cMoment);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors(this.code)) {
			Sponsorship sp;
			sp = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(sp == null, this.code, "sponsor.sponsorship.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors(this.amount)) {
			super.state(this.validator.validateMoneyQuantity(object.getAmount()), this.amount, "sponsor.sponsorship.form.error.amount");
			super.state(this.validator.validateMoneyCurrency(object.getAmount()), this.amount, "sponsor.sponsorship.form.error.currency");
		}
		if (!super.getBuffer().getErrors().hasErrors(this.startDate)) {
			super.state(MomentHelper.isAfterOrEqual(object.getStartDate(), MomentHelper.getCurrentMoment()), this.startDate, "sponsor.sponsorship.form.error.start-date");
			super.state(this.validator.validateDate(object.getStartDate()), this.startDate, "sponsor.sponsorship.form.error.date");
		}
		if (!super.getBuffer().getErrors().hasErrors(this.endDate)) {
			super.state(this.validator.validateDate(object.getEndDate()), this.endDate, "sponsor.sponsorship.form.error.date");
			if (object.getStartDate() != null)
				super.state(this.validator.validateMoment(object.getStartDate(), object.getEndDate()), this.endDate, "sponsor.sponsorship.form.error.end-date");
		}
		if (object.getProject().isDraftMode())
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
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
		dataset = super.unbind(object, this.code, this.amount, this.moment, this.startDate, this.endDate, this.type, this.contact, this.link, this.draftMode, this.sponsor, this.project);
		final SelectChoices choices = new SelectChoices();
		Collection<Project> projects;
		projects = this.repository.findAllPublishedProjects();
		for (final Project p : projects) {
			boolean isSelected = this.validator.isSelectedProject(object, p);
			choices.add(String.valueOf(p.getId()), String.format("%s -> %s", p.getCode(), p.getTitle()), isSelected);
		}
		dataset.put("projects", choices);
		SelectChoices types = SelectChoices.from(SponsorshipType.class, object.getType());
		dataset.put("types", types);
		super.getResponse().addData(dataset);
	}
}
