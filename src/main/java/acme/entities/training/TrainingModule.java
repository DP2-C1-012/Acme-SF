
package acme.entities.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				creationMoment;

	@NotBlank
	@Length(max = 100)
	private String				details;

	private DifficultyLevel		difficultyLevel;

	//After(creationMoment)
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				updateMoment;

	@URL
	private String				link;


	@Transient
	public Integer getTotalTime() {
		if (this.updateMoment != null)
			return (int) (this.updateMoment.getTime() - this.creationMoment.getTime());
		else
			return (int) (new Date().getTime() - this.creationMoment.getTime());
	}
}
