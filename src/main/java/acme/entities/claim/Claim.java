
package acme.entities.claim;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class Claim extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "C-[0-9]{4}")
	@NotBlank
	@Column(unique = true)
	@NotNull
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				instantiationMoment;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				heading;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				description;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				department;

	@Email
	private String				email;

	@URL
	private String				link;

	@NotNull
	@Transient
	private boolean				confirmation;
}
