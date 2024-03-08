
package acme.entities.systemConfiguration;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@NotNull
	@NotBlank
	private String				defaultCurrency;

	@NotNull
	@NotBlank
	private String				acceptedCurrencies;

}
