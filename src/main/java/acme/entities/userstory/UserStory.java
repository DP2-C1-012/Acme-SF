
package acme.entities.userstory;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class UserStory extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	private String				title;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@Valid
	@NotNull
	private Money				estimatedCost;

	@NotBlank
	@Length(max = 101)
	private String				acceptanceCriteria;

	private Priority			priority;

	@URL
	private String				link;

	@ManyToOne(optional = false)
	@Valid
	private Manager				manager;

}
