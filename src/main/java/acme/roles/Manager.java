
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Manager extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 76)
	private String				degree;

	@NotBlank
	@Length(max = 101)
	private String				overview;

	@NotBlank
	@Length(max = 101)
	private String				certifications;

	private String				link;

}
