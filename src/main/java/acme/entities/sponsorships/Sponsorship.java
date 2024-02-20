
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Sponsorship extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@ManyToOne
	private Project				project;

	@Pattern(regexp = "[A-Z]{1,3}-[0,9]{3}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@FutureOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	public Date					duration;

	@Range(min = 1)
	private Integer				amount;

	private Type				type;

	@Email
	private String				contact;

	@URL
	private String				link;

}
