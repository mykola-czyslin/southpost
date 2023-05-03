package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.model.StreetKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface definition for the {@link Street} entity.
 * Created by mchys on 25.12.2018.
 */
public interface StreetRepository extends JpaRepository<Street, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "searchValue");

	@Query("select count(s) from Street as s\n " +
			"where (:regionId is null or s.settlement.district.region.id = :regionId)\n" +
			"  and (:districtId is null or s.settlement.district.id = :districtId)\n" +
			"  and (:settlementKind is null or s.settlement.kind = :settlementKind)\n" +
			"  and (:settlementPattern is null or s.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
			"  and (:kind is null or s.kind = :kind)\n " +
			"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')")
	long count(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementPattern") String settlementNamePattern,
			@Param("kind") StreetKind streetKind,
			@Param("pattern") String streetNamePattern
	);

	@Query("select s from Street as s\n " +
			"where (:regionId is null or s.settlement.district.region.id = :regionId)\n" +
			"  and (:districtId is null or s.settlement.district.id = :districtId)\n" +
			"  and (:settlementKind is null or s.settlement.kind = :settlementKind)\n" +
			"  and (:settlementPattern is null or s.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
			"  and (:kind is null or s.kind = :kind)\n " +
			"  and (:pattern is null or s.searchValue like upper(:pattern) escape '\\\\')")
	List<Street> list(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementPattern") String settlementNamePattern,
			@Param("kind") StreetKind streetKind,
			@Param("pattern") String streetNamePattern,
			Pageable pageable
	);

	@Query("select s from Street as s\n " +
			"where s.searchValue = upper(:name)\n " +
			"  and s.kind = :kind\n " +
			"  and s.settlement.id = :settlementId")
	Optional<Street> findUnique(
			@Param("name") String name,
			@Param("kind") StreetKind kind,
			@Param("settlementId") Long settlementId
	);
}
