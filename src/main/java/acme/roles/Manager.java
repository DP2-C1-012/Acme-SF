
package acme.roles;

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
public class Manager extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	@NotNull
	private String				degree;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				overview;

	@NotBlank
	@Length(max = 100)
	@NotNull
	private String				certifications;

	@URL
	@Length(max = 255)
	private String				link;

}
