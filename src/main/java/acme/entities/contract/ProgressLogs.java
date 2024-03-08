
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProgressLogs extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Pattern(regexp = "PG-[A-Z]{1-2}-[0-9]{4}")
	private String				recordId;

	@Min(1)
	private String				completeness;

	@NotBlank
	@Length(max = 100)
	private String				comment;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@NotBlank
	@Length(max = 100)
	private String				goals;

	@NotBlank
	@Length(max = 75)
	private String				responsible;

}
