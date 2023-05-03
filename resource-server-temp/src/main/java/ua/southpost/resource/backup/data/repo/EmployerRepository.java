package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.model.SettlementKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The repository interface definition for the {@link Employer} entity.
 * Created by mchys on 25.12.2018.
 */
public interface EmployerRepository extends JpaRepository<Employer, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "searchValue");

	@Query("select count(emp) from Employer as emp \n " +
			"where (:namePattern is null or emp.searchValue like upper(:namePattern) escape '\\\\')\n" +
			"  and (:webSiteSignificant = 0 or :webSitePattern is null and emp.website is null or lower(emp.website) like lower(:webSitePattern) escape '\\\\')\n" +
			"  and (:settlementName is null or emp.settlement.searchValue like upper(:settlementName) escape '\\\\' )\n" +
			"  and (:settlementKind is null or emp.settlement.kind = :settlementKind)\n" +
			"  and (:districtId is null or (emp.settlement.district is not null and emp.settlement.district.id = :districtId))\n" +
			"  and (:regionId is null or emp.settlement.district.region.id = :regionId)")
	long count(
			@Param("namePattern") String namePattern,
			@Param("webSiteSignificant") int webSiteSignificant,
			@Param("webSitePattern") String webSitePattern,
			@Param("settlementName") String settlementName,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("districtId") Long districtId,
			@Param("regionId") String regionId
	);

	@Query("select emp from Employer as emp \n " +
			"where (:namePattern is null or emp.searchValue like upper(:namePattern) escape '\\\\')\n" +
			"  and (:webSiteSignificant = 0 or :webSitePattern is null and emp.website is null or lower(emp.website) like lower(:webSitePattern) escape '\\\\')\n" +
			"  and (:settlementName is null or emp.settlement.searchValue like upper(:settlementName) escape '\\\\' )\n" +
			"  and (:settlementKind is null or emp.settlement.kind = :settlementKind)\n" +
			"  and (:districtId is null or (emp.settlement.district is not null and emp.settlement.district.id = :districtId))\n" +
			"  and (:regionId is null or emp.settlement.district.region.id = :regionId)")
	List<Employer> list(
			@Param("namePattern") String namePattern,
			@Param("webSiteSignificant") int webSiteSignificant,
			@Param("webSitePattern") String webSitePattern,
			@Param("settlementName") String settlementName,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("districtId") Long districtId,
			@Param("regionId") String regionId,
			Pageable pageable
	);

	@Query("select emp from Employer as emp where emp.website = lower(:website)")
	Optional<Employer> findByWebSite(@Param("website") String website);

	@Query("select emp from Employer as emp where emp.searchValue = upper(:name)")
	Optional<Employer> findByName(@Param("name") String name);
}
