
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private int					totalDynamicCodeAudits;
	private int					totalStaticCodeAudits;
	private int					minAuditRecords;
	private int					maxAuditRecords;
	private double				averageNumAuditRecords;
	private double				deviationNumAuditRecords;
	private double				minPeriodLength;
	private double				maxPeriodLength;
	private double				averageNumPeriodLength;
	private double				deviationNumPeriodLength;
}
