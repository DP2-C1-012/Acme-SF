
package acme.features.client.progresslog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.progress_logs.ProgressLogs;
import acme.roles.Client;

@Service
public class ClientProgressLogPublishService extends AbstractService<Client, ProgressLogs> {

	@Autowired
	protected ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		ProgressLogs object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.getProgressLogsById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getContract().getClient().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		ProgressLogs object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.getProgressLogsById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLogs object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		ProgressLogs object2;
		int id;

		id = super.getRequest().getData("id", int.class);
		object2 = this.repository.getProgressLogsById(id);
		object.setContract(object2.getContract());
		super.bind(object, "recordId", "completeness", "comment", "moment", "responsible");
	}

	@Override
	public void validate(final ProgressLogs object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			ProgressLogs existing;
			existing = this.repository.getProgressLogsByRecordId(object.getRecordId());
			final ProgressLogs progressLog2 = object.getRecordId().equals("") || object.getRecordId() == null ? null : this.repository.getProgressLogsByRecordId(object.getRecordId());
			super.state(existing == null || progressLog2.equals(existing), "code", "client.contract.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("moment"))
			super.state(MomentHelper.isBefore(object.getMoment(), MomentHelper.getCurrentMoment()), "instantiationMoment", "client.progressLogs.form.error.moment");
	}

	@Override
	public void perform(final ProgressLogs object) {
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLogs object) {
		if (object == null)
			throw new IllegalArgumentException("No object found");
		Dataset dataset;
		dataset = super.unbind(object, "recordId", "completeness", "comment", "moment", "responsible", "contract", "draftMode");
		dataset.put("contractTitle", object.getContract().getCode());
		super.getResponse().addData(dataset);
	}
}
