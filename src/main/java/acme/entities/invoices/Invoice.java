
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.sponsorships.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Invoice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "IN-[0,9]{4}-[0,9]{4}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				dueDate;

	@Min(1)
	private Integer				quantity;

	@Min(0)
	private Integer				tax;

	@URL
	private String				link;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Integer totalAmount() {
		return this.quantity + this.tax;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne
	private Sponsorship sponsorship;

}
