//
//package acme.features.sponsor.sponsorship;
//
//import java.time.temporal.ChronoUnit;
//import java.util.Collection;
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
//import org.springframework.stereotype.Service;
//
//import acme.client.data.models.Dataset;
//import acme.client.helpers.MomentHelper;
//import acme.client.services.AbstractService;
//import acme.client.views.SelectChoices;
//import acme.entities.companies.Company;
//import acme.entities.sponsorships.Sponsorship;
//import acme.roles.Sponsor;
//
//@Service
//public class SponsorSponsorshipsCreateService extends AbstractService<Sponsor, Sponsorship> {
//
//	// Internal state ---------------------------------------------------------
//
//	@Autowired
//	private SponsorSponsorshipRepository repository;
//
//	// AbstractService interface ----------------------------------------------
//
//
//	@Override
//	public void authorise() {
//		super.getResponse().setAuthorised(true);
//	}
//
//	@Override
//	public void load() {
//		Sponsorship object;
//		Sponsor sponsor;
//
//		sponsor = this.repository.findOneSponsorById(super.getRequest().getPrincipal().getActiveRoleId());
//		object = new Sponsorship();
//		object.setDraftMode(true);
//		object.setSponsor(sponsor);
//
//		super.getBuffer().addData(object);
//	}
//
//	@Override
//	public void bind(final Sponsorship object) {
//		assert object != null;
//
//		int contractorId;
//		Company contractor;
//
//		contractorId = super.getRequest().getData("contractor", int.class);
//		contractor = this.repository.findOneContractorById(contractorId);
//
//		super.bind(object, "reference", "title", "deadline", "salary", "score", "moreInfo", "description");
//		object.setContractor(contractor);
//	}
//
//	@Override
//	public void validate(final Job object) {
//		assert object != null;
//
//		if (!super.getBuffer().getErrors().hasErrors("reference")) {
//			Job existing;
//
//			existing = this.repository.findOneJobByReference(object.getReference());
//			super.state(existing == null, "reference", "employer.job.form.error.duplicated");
//		}
//
//		if (!super.getBuffer().getErrors().hasErrors("deadline")) {
//			Date minimumDeadline;
//
//			minimumDeadline = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
//			super.state(MomentHelper.isAfter(object.getDeadline(), minimumDeadline), "deadline", "employer.job.form.error.too-close");
//		}
//
//		if (!super.getBuffer().getErrors().hasErrors("salary"))
//			super.state(object.getSalary().getAmount() > 0, "salary", "employer.job.form.error.negative-salary");
//	}
//
//	@Override
//	public void perform(final Job object) {
//		assert object != null;
//
//		this.repository.save(object);
//	}
//
//	@Override
//	public void unbind(final Job object) {
//		assert object != null;
//
//		int employerId;
//		Collection<Company> contractors;
//		SelectChoices choices;
//		Dataset dataset;
//
//		employerId = super.getRequest().getPrincipal().getActiveRoleId();
//		contractors = this.repository.findManyContractorsByEmployerId(employerId);
//		choices = SelectChoices.from(contractors, "name", object.getContractor());
//
//		dataset = super.unbind(object, "reference", "title", "deadline", "salary", "score", "moreInfo", "description", "draftMode");
//		dataset.put("contractor", choices.getSelected().getKey());
//		dataset.put("contractors", choices);
//
//		super.getResponse().addData(dataset);
//	}
//}
