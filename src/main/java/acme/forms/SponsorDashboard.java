
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	int							numberOfInvoicesWithATaskLessOrEqualThan21;
	int							numberOfSponsorshipsWithALink;
	double						averageAmountOfSponsorships;
	double						deviationAmountOfSponsorships;
	double						minimumAmountOfSponsorships;
	double						maximumAmountOfSponsorships;
	double						averageQuantityOfInvoices;
	double						deviationQuantityOfInvoices;
	double						minimumQuantityOfInvoicess;
	double						maximumQuantityOfInvoices;
}
