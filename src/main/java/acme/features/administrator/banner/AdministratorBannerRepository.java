
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.banner.Banner;

public interface AdministratorBannerRepository extends AbstractRepository {

	@Query("SELECT b FROM Banner b")
	Collection<Banner> findAllBanners();

	@Query("SELECT b FROM Banner b WHERE b.id = :id")
	Banner findOneBannerByID(int id);

}
