
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						numberOfShouldUs;
	Integer						numberOfMustUs;
	Integer						numberOfCouldUs;
	Integer						numberOfWontUs;
	Integer						averageUserStoryCost;
	Integer						deviationOfUserStoryCost;
	Integer						minimumUserStoryCost;
	Integer						maximumUserStoryCost;
	Integer						averageProjectCost;
	Integer						deviationProjectCost;
	Integer						minimumProjectCost;
	Integer						maximumProjectCost;

}
