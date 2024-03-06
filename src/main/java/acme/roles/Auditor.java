
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@NotNull
	@Length(max = 75)
	private String				firm;
	@NotBlank
	@NotNull
	@Length(max = 25)
	@Column(unique = true)
	private String				professionalID;
	@NotBlank
	@NotNull
	@Length(max = 100)
	private String				certifications;
	@NotBlank
	@NotNull
	@URL
	private String				moreInfo;
}
