
package acme.entities.risk;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Risk extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "R-[0-9]{3}")
	@NotBlank
	@Column(unique = true)
	private String				reference;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				identificationDate;

	@Min(0)
	private Integer				impact;

	private Integer				probability;


	public double value() {
		return this.impact * this.probability;
	}


	@NotBlank
	@Length(max = 101)
	private String	description;

	@URL
	private String	link;

}
