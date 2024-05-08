
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	protected static final long	serialVersionUID	= 1L;

	private Integer				numOfTrainingModuleOfDeveloper;
	private Integer				numTrainingSessionsWithLink;
	private Double				averageTrainingModuleTime;
	private Double				deviationTrainingModuleTime;
	private Integer				minTrainingModuleTime;
	private Integer				maxTrainingModuleTime;

}
