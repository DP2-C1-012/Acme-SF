
package acme.forms;

import javax.persistence.Entity;
import javax.validation.constraints.Max;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -----------------------------------------------------
	@Max(21)
	private Integer				invoices;

	private Integer				invAverage;

	private Integer				invDeviation;

	private Integer				invMinimum;

	private Integer				invMaximum;

	private Integer				sponsorships;

	private Integer				spAverage;

	private Integer				spDeviation;

	private Integer				spMinimum;

	private Integer				spMaximum;

}
