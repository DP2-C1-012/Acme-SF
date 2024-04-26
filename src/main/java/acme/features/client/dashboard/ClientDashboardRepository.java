
package acme.features.client.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Client;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and pl.completeness <=25 and pl.draftMode=false")
	Optional<Integer> findNumOfprogressLogsLess25(Client client);

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and pl.completeness <= 50 and 25 <= pl.completeness and pl.draftMode=false")
	Optional<Integer> findNumOfProgressLogsWithRate25To50(Client client);

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and pl.completeness <= 75 and 50 <= pl.completeness and pl.draftMode=false")
	Optional<Integer> findNumOfProgressLogsWithRate50To75(Client client);

	@Query("select count(pl) from ProgressLogs pl where pl.contract.client = :client and 75 <= pl.completeness and pl.draftMode=false")
	Optional<Integer> findNumOfProgressLogsWithRateOver75(Client client);

	@Query("select avg(c.budget.amount) from Contract c where c.client = :client and c.draftMode=false")
	Optional<Double> findAverageContractBudget(Client client);

	@Query("select max(c.budget.amount) from Contract c where c.client = :client and c.draftMode=false")
	Optional<Double> findMaxContractBudget(Client client);

	@Query("select min(c.budget.amount) from Contract c where c.client = :client and c.draftMode=false")
	Optional<Double> findMinContractBudget(Client client);

	@Query("select stddev(c.budget.amount) from Contract c where c.client = :client and c.draftMode=false")
	Optional<Double> findLinearDevContractBudget(Client client);

	@Query("select m from Client m where m.userAccount.id = :id")
	Client findOneClientByUserAccountId(int id);
}
