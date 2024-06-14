
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	@Index(columnList = "id"), @Index(columnList = "manager_id"), @Index(columnList = "code"), @Index(columnList = "manager_id,draftMode")
})
public class Project extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Pattern(regexp = "[A-Z]{3}-[0-9]{4}")
	@NotBlank
	@Column(unique = true)
	@NotNull
	private String				code;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				title;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				abstrac;

	@NotNull
	private Money				cost;

	@Transient
	@NotNull
	private boolean				indication;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				draftMode;

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	private Manager				manager;

}
