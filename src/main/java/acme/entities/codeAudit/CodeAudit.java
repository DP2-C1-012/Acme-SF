
package acme.entities.codeAudit;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.auditRecord.Mark;
import acme.entities.projects.Project;
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
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	private String				code;
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				executionDate;
	@NotNull
	private Type				type;
	@Length(max = 100)
	@NotBlank
	@NotNull
	private String				correctiveActions;
	@NotNull
	private Mark				mark				= Mark.F_MINUS;
	@URL
	private String				optionalLink;
	private boolean				published;
	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private Auditor				auditor;
	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private Project				project;

}
