
package acme.features.client.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ClientDashboard;
import acme.roles.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	@Autowired
	protected ClientDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final ClientDashboard dashboard = new ClientDashboard();

		Principal p = super.getRequest().getPrincipal();
		final Client client = this.repository.getClientByUserAccountId(p.getAccountId());

		final double findAverageContractBudget = this.repository.getAverageContractBudget(client).orElse(0.0);
		final double findMaxContractBudget = this.repository.getMaxContractBudget(client).orElse(0.0);
		final double findMinContractBudget = this.repository.getMinContractBudget(client).orElse(0.0);
		final double findLinearDevContractBudget = this.repository.getLinearDevContractBudget(client).orElse(0.0);
		final Integer progressLogsWithRateBelow25 = this.repository.getNumPLLess25(client).orElse(0);
		final Integer progressLogsWithRate25To50 = this.repository.getNumPLWithRate25To50(client).orElse(0);
		final Integer progressLogsWithRate50To75 = this.repository.getNumPLWithRate50To75(client).orElse(0);
		final Integer progressLogsWithRateOver75 = this.repository.getNumPLWithRateOver75(client).orElse(0);

		dashboard.setAverageBudget(findAverageContractBudget);
		dashboard.setMinimumBudget(findMinContractBudget);
		dashboard.setMaximunBudget(findMaxContractBudget);
		dashboard.setDesviationBudget(findLinearDevContractBudget);
		dashboard.setNumPLCompletenessBelow25(progressLogsWithRateBelow25);
		dashboard.setNumPLCompletenessBetween25To50(progressLogsWithRate25To50);
		dashboard.setNumPLCompletenessBetween50To75(progressLogsWithRate50To75);
		dashboard.setNumPLCompletenessAbove75(progressLogsWithRateOver75);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ClientDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "numPLCompletenessBelow25", "numPLCompletenessBetween25To50", "numPLCompletenessBetween50To75", "numPLCompletenessAbove75", "averageBudget", "desviationBudget", "minimumBudget", "maximunBudget");

		super.getResponse().addData(dataset);
	}

}
