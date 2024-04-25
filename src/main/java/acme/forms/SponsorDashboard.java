
package acme.forms;

import java.util.Collection;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	int							numberOfInvoicesWithATaskLessOrEqualThan21;
	int							numberOfSponsorshipsWithALink;
	Collection<Double>			averageAmountOfSponsorships;
	Collection<Double>			deviationAmountOfSponsorships;
	Collection<Double>			minimumAmountOfSponsorships;
	Collection<Double>			maximumAmountOfSponsorships;
	Collection<Double>			averageQuantityOfInvoices;
	Collection<Double>			deviationQuantityOfInvoices;
	Collection<Double>			minimumQuantityOfInvoices;
	Collection<Double>			maximumQuantityOfInvoices;
}
