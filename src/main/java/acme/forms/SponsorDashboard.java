
package acme.forms;

import java.util.Collection;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	private int					numberOfInvoicesWithATaskLessOrEqualThan21;
	private int					numberOfSponsorshipsWithALink;
	private Collection<Double>	averageAmountOfSponsorships;
	private Collection<Double>	deviationAmountOfSponsorships;
	private Collection<Double>	minimumAmountOfSponsorships;
	private Collection<Double>	maximumAmountOfSponsorships;
	private Collection<Double>	averageQuantityOfInvoices;
	private Collection<Double>	deviationQuantityOfInvoices;
	private Collection<Double>	minimumQuantityOfInvoices;
	private Collection<Double>	maximumQuantityOfInvoices;
}
