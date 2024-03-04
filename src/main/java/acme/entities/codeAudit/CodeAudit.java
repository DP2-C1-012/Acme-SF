
package acme.entities.codeAudit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CodeAudit extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;
	@NotBlank
	@NotNull
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3")
	private String				code;
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;
	private Type				type;
	@Length(max = 100)
	@NotBlank
	@NotNull
	private String				correctiveActions;
	private Mark				mark;
	@URL
	private String				optionalLink;
	@ManyToOne(optional = false)
	private Auditor				auditor;
}
