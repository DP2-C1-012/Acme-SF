
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	Integer						totalPrincipalsEachRole;
	Double						ratioOfNotice;
	Double						ratioOfObjective;
	Integer						averageRiskValue;
	Integer						minimumRiskValue;
	Integer						maximumRiskValue;
	Integer						standardDeviationRiskValue;
	Integer						averageClaimsOver10Weeks;
	Integer						minimumClaimsOver10Weeks;
	Integer						maximumClaimsOver10Weeks;
	Integer						standardDeviationClaimsOver10Weeks;

}
