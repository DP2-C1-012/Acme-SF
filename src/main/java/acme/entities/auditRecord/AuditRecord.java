
package acme.entities.auditRecord;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.codeAudit.CodeAudit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AuditRecord extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;
	@NotBlank
	@NotNull
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	private String				code;
	@Temporal(TemporalType.TIMESTAMP)
	private Date				periodStart;
	@Temporal(TemporalType.TIMESTAMP)
	private Date				periodEnd;
	private Mark				mark;
	@URL
	private String				optionalLink;
	@ManyToOne(optional = false)
	private CodeAudit			codeAudit;
}
