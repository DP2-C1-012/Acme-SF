
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	protected ManagerDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final ManagerDashboard dashboard = new ManagerDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Manager manager = this.repository.findManagerByUserAccountId(userAccountId);
		final int numberOfUserStory = this.repository.findNumOfUserStories(manager).orElse(0);
		final int averageUserStoryCost = this.repository.findAverageUserStoryCost(manager).orElse(0);
		final int devUserStoryCost = this.repository.findDevUserStoryCost(manager).orElse(0);
		final int minUserStoryCost = this.repository.findMinUserStoryCost(manager).orElse(0);
		final int maxUserStoryCost = this.repository.findMaxUserStoryCost(manager).orElse(0);
		final int averageProjectCost = this.repository.findAverageProjectCost(manager).orElse(0);
		final int maxProjectCost = this.repository.findMaxProjectCost(manager).orElse(0);
		final int minProjectCost = this.repository.findMinProjectCost(manager).orElse(0);
		final int devProjectCost = this.repository.findDevProjectCost(manager).orElse(0);
		dashboard.setUserStoryCount(numberOfUserStory);
		dashboard.setAverageUserStoryCost(averageUserStoryCost);
		dashboard.setDeviationOfUserStoryCost(devUserStoryCost);
		dashboard.setMinimumUserStoryCost(minUserStoryCost);
		dashboard.setMaximumUserStoryCost(maxUserStoryCost);
		dashboard.setAverageProjectCost(averageProjectCost);
		dashboard.setDeviationProjectCost(devProjectCost);
		dashboard.setMinimumProjectCost(minProjectCost);
		dashboard.setMaximumProjectCost(maxProjectCost);
		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "userStoryCount", "averageUserStoryCost", "deviationOfUserStoryCost", "minimumUserStoryCost", "maximumUserStoryCost", "averageProjectCost", "deviationProjectCost", "minimumProjectCost", "maximumProjectCost");
		super.getResponse().addData(dataset);

	}

}
