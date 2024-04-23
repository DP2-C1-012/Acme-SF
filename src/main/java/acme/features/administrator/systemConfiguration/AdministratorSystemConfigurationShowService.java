
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SystemConfiguration sysCon = this.repository.findSystemConfiguration();
		super.getBuffer().addData(sysCon);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		Dataset data = super.unbind(object, "acceptedCurrencies", "defaultCurrency");
		super.getResponse().addData(data);
	}
}
