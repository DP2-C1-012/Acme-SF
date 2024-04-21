
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select ct from Contract ct")
	Collection<Contract> getAllContract();

	@Query("select ct from Contract ct where ct.id = :id")
	Contract getContractById(int id);

}
