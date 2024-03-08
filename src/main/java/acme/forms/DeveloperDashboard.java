
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	/*
	 * total number of training modules with an update moment;
	 * total number of training sessions with a link;
	 * average,deviation, minimum and maximum time of the training modules.
	 */

	protected static final long	serialVersionUID	= 1L;

	private Integer				numTrainingModulesWithUpdateMoment;
	private Integer				numTrainingSessionsWithLink;
	private Double				averageAmountOfTrainingModules;
	private Double				deviationAmountOfTrainingModules;
	private Double				minimumAmountOfTrainingModules;
	private Double				maximumAmountOfTrainingModules;;

}
