
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

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

	@Override
	public void bind(final SystemConfiguration object) {
		super.bind(object, "acceptedCurrencies", "defaultCurrency");
		if (object.getAcceptedCurrencies() != null) {
			String[] currencies = object.getAcceptedCurrencies().trim().split(",", -1);
			String newCurr = "";
			for (int i = 0; i < currencies.length; i++)
				if (i == currencies.length - 1)
					newCurr = newCurr + currencies[i];
				else
					newCurr = newCurr + currencies[i] + ",";

			object.setAcceptedCurrencies(newCurr);
		}
	}

	@Override
	public void validate(final SystemConfiguration object) {
		if (!super.getBuffer().getErrors().hasErrors("acceptedCurrencies")) {
			boolean state = object.getAcceptedCurrencies() != null;
			if (state) {
				String[] currencies = object.getAcceptedCurrencies().trim().split(",", -1);
				for (int i = 0; i < currencies.length; i++) {
					String curr = currencies[i];
					state = curr.matches("^[A-Z]{3}$") && !curr.isBlank();
					if (!state)
						break;
				}
			}
			super.state(state, "acceptedCurrencies", "administrator.systemConfiguration.error.acceptedCurrencies");
		}
		if (!super.getBuffer().getErrors().hasErrors("defaultCurrency")) {
			boolean state = object.getDefaultCurrency() != null;
			if (state) {
				state = false;
				String[] currencies = object.getAcceptedCurrencies().split(",");
				for (String curr : currencies) {
					state = curr.matches(object.getDefaultCurrency());
					if (state)
						break;
				}
			}
			super.state(state, "defaultCurrency", "administrator.systemConfiguration.error.defaultCurrency");
		}

	}

	@Override
	public void perform(final SystemConfiguration object) {
		this.repository.save(object);
	}
}
