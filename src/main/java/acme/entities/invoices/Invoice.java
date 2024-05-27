
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.sponsorships.Sponsorship;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
	@Index(columnList = "sponsorship_id"), @Index(columnList = "id"), @Index(columnList = "code")
})
public class Invoice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	@NotBlank
	@Column(unique = true)
	@NotNull
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				registrationTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				dueDate;

	@NotNull
	private Money				quantity;

	@NotNull
	private Money				tax;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Money totalAmount() {
		double total = this.quantity.getAmount() + this.tax.getAmount();
		Money totalAmount = new Money();
		totalAmount.setAmount(total);
		totalAmount.setCurrency(this.quantity.getCurrency());
		return totalAmount;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	private Sponsorship sponsorship;

}
