
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	/*
	 * Attributes
	 */

	private Integer				numPLCompletenessBelow25;
	private Integer				numPLCompletenessBetween25To50;
	private Integer				numPLCompletenessBetween50To75;
	private Integer				numPLCompletenessAbove75;
	private Double				averageBudget;
	private Double				desviationBudget;
	private Double				minimumBudget;
	private Double				maximunBudget;

}
