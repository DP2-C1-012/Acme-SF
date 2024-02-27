
package acme.entities.training;

import java.sql.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;

public class TrainingSession extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	//Revisar
	@Past
	@NotNull
	private Date				period;

	@NotBlank
	@Length(max = 75)
	private String				location;

	@NotBlank
	@Length(max = 75)
	private String				instructor;

	@NotBlank
	@NotNull
	@Email
	private String				email;

	@URL
	private String				link;

}
