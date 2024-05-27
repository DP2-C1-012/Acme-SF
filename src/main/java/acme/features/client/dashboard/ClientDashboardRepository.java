
package acme.features.client.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Client;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and pl.completeness <=25 and pl.draftMode=false")
	Optional<Integer> getNumPLLess25(Client client);

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and pl.completeness <= 50 and 25 <= pl.completeness and pl.draftMode=false")
	Optional<Integer> getNumPLWithRate25To50(Client client);

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and pl.completeness <= 75 and 50 <= pl.completeness and pl.draftMode=false")
	Optional<Integer> getNumPLWithRate50To75(Client client);

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and 75 <= pl.completeness and pl.draftMode=false")
	Optional<Integer> getNumPLWithRateOver75(Client client);

	@Query("select avg(ct.budget.amount) from Contract ct where ct.client = :client and ct.draftMode=false")
	Optional<Double> getAverageContractBudget(Client client);

	@Query("select max(ct.budget.amount) from Contract ct where ct.client = :client and ct.draftMode=false")
	Optional<Double> getMaxContractBudget(Client client);

	@Query("select min(ct.budget.amount) from Contract ct where ct.client = :client and ct.draftMode=false")
	Optional<Double> getMinContractBudget(Client client);

	@Query("select stddev(ct.budget.amount) from Contract ct where ct.client = :client and ct.draftMode=false")
	Optional<Double> getLinearDevContractBudget(Client client);

	@Query("select cl from Client cl where cl.userAccount.id = :id")
	Client getClientByUserAccountId(int id);
}
