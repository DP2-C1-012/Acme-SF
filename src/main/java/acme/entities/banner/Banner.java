
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
	@Index(columnList = "id")
})
public class Banner extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)

	private Date				instantiationMomment;
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)

	private Date				updateMomment;
	@Temporal(TemporalType.TIMESTAMP)

	private Date				displayPeriodStart;
	@Temporal(TemporalType.TIMESTAMP)

	private Date				displayPeriodEnd;
	@NotBlank
	@NotNull
	@Length(max = 75)

	private String				slogan;
	@URL
	@NotBlank
	@NotNull
	@Length(max = 255)
	private String				pictureURL;
	@URL
	@NotBlank
	@NotNull
	@Length(max = 255)
	private String				link;

}
