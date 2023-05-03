package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.model.SettlementKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository definition interface for the {@link Settlement} entity.
 * Created by mchys on 25.12.2018.
 */
public interface SettlementRepository extends JpaRepository<Settlement, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "searchValue");

	@Query("select count(s) from Settlement as s\n " +
			"where (:regionId is null or s.district.region.id = :regionId)\n" +
			"  and (:districtId is null or s.district.id = :districtId)\n" +
			"  and (:kind is null or s.kind = :kind)\n" +
			"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')")
	long count(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("kind") SettlementKind settlementKind,
			@Param("pattern") String settlementNamePattern
	);

	@Query("select s from Settlement as s\n " +
			"where (:regionId is null or s.district.region.id = :regionId)\n" +
			"  and (:districtId is null or s.district.id = :districtId)\n" +
			"  and (:kind is null or s.kind = :kind)\n" +
			"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')")
	List<Settlement> list(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("kind") SettlementKind settlementKind,
			@Param("pattern") String settlementNamePattern,
			Pageable pageable
	);

	@Query("select s from Settlement as s \n" +
			" where s.district.id = :districtId\n" +
			"   and s.kind = :kind \n" +
			"   and s.searchValue = upper(:name)")
	Optional<Settlement> findUnique(
			@Param("districtId") Long districtId,
			@Param("kind") SettlementKind kind,
			@Param("name") String name
	);
}
