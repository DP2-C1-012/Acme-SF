
package acme.entities.userstory;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(indexes = {
	@Index(columnList = "id"), @Index(columnList = "manager_id")
})
public class UserStory extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				title;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				description;

	@NotNull
	private Money				estimatedCost;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				acceptanceCriteria;

	@NotNull
	private Priority			priority;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				draftMode;

	@ManyToOne(optional = false)
	@Valid
	private Manager				manager;

}
