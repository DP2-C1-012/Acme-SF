
package acme.entities.progress_logs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.contract.Contract;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProgressLogs extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	/*
	 * Attributes
	 */

	@NotNull
	@NotBlank
	@Pattern(regexp = "PG-[A-Z]{1,2}-[0-9]{4}")
	private String				recordId;

	@NotNull
	@Min(1)
	private Double				completeness;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				comment;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				moment;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				responsible;

	/*
	 * Relations
	 */

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Contract			contract;

}
