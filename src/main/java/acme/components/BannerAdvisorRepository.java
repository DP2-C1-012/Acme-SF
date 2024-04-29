
package acme.components;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

@Repository
public interface BannerAdvisorRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b WHERE b.displayPeriodStart <= :moment AND b.displayPeriodEnd >= :moment")
	Collection<Banner> findAllBannersAvailableFor(Date moment);
	@Query("SELECT b FROM Banner b")
	Collection<Banner> findAllBanners();

}
