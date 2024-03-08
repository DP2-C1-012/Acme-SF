
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Contract extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "[A-Z]{1-3}-[0-9]{3}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				moment;

	@NotBlank
	@Length(max = 75)
	private String				provider;

	@NotBlank
	@Length(max = 75)
	private String				customer;

	@NotBlank
	@Length(max = 100)
	private String				goals;

	private Double				budget;

}
