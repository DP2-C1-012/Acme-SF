
package acme.features.client.progresslog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.progress_logs.ProgressLogs;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("select pl from ProgressLogs pl where pl.contract.client.userAccount.id = :id")
	Collection<ProgressLogs> getProgressLogsByClientId(int id);

	@Query("select pl from ProgressLogs pl where pl.contract.id = :id")
	Collection<ProgressLogs> getProgressLogsByContractId(int id);

	@Query("select pl from ProgressLogs pl where pl.recordId = :id")
	ProgressLogs getProgressLogsByRecordId(String id);

	@Query("select pl from ProgressLogs pl where pl.id = :id")
	ProgressLogs getProgressLogsById(int id);

	@Query("select ct from Contract ct where ct.id = :id")
	Contract getContractById(int id);

	@Query("select ct from Contract ct where ct.code = :code")
	Contract getContractByCode(String code);

	@Query("select ct from Contract ct where ct.client.id = :id")
	Collection<Contract> getContractsByClient(int id);
}
