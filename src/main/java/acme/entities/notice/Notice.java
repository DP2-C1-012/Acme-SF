
package acme.entities.notice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notice extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				moment;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				title;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				author;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				message;

	@Email
	private String				email;

	@URL
	private String				link;
}
