package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface definition for the {@link District} entity.
 * Created by mchys on 25.12.2018.
 */
public interface DistrictRepository extends JpaRepository<District, Long> {
	@Query("select d from District d where d.region.id = :regionId order by d.searchValue")
	List<District> list(@Param("regionId") String regionId);
}
