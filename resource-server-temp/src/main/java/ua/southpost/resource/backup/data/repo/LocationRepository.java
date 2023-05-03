package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * The repository interface definition for the {@link Location} entity.
 * Created by mchys on 25.12.2018.
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "postalCode", "street.searchValue", "searchStreetNumber", "searchRoomNumber", "roomNumber");

	@Query("select count(l) from Location as l\n" +
			"where (:regionId is null or l.street.settlement.district.region.id = :regionId)\n" +
			"  and (:districtId is null or l.street.settlement.district.id = :districtId)\n" +
			"  and (:settlementKind is null or l.street.settlement.kind = :settlementKind)\n" +
			"  and (:settlementPattern is null or l.street.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
			"  and (:streetKind is null or l.street.kind = :streetKind)\n " +
			"  and (:streetPattern is null or l.street.searchValue like upper(:streetPattern) escape '\\\\')\n " +
			"  and (:postal is null or l.postalCode like :postal escape '\\\\')\n " +
			"  and (:streetNumber is null or l.searchStreetNumber like upper(:streetNumber) escape '\\\\') \n " +
			"  and (:blockNumber is null or l.searchBlockNumber like upper(:blockNumber) escape '\\\\')\n " +
			"  and (:roomNumber is null or l.searchRoomNumber like upper(:roomNumber) escape '\\\\')"
	)
	long count(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementPattern") String settlementNamePattern,
			@Param("streetKind") StreetKind streetKind,
			@Param("streetPattern") String streetNamePattern,
			@Param("postal") String postalCodePattern,
			@Param("streetNumber") String streetNumberPattern,
			@Param("blockNumber") String blockNumberPattern,
			@Param("roomNumber") String roomNumberPattern
	);

	@Query("select l from Location as l\n " +
			"where (:regionId is null or l.street.settlement.district.region.id = :regionId)\n" +
			"  and (:districtId is null or l.street.settlement.district.id = :districtId)\n" +
			"  and (:settlementKind is null or l.street.settlement.kind = :settlementKind)\n" +
			"  and (:settlementPattern is null or l.street.settlement.searchValue like upper(:settlementPattern) escape '\\\\')\n " +
			"  and (:streetKind is null or l.street.kind = :streetKind)\n " +
			"  and (:streetPattern is null or l.street.searchValue like upper(:streetPattern) escape '\\\\')\n " +
			"  and (:postal is null or l.postalCode like :postal escape '\\\\')\n " +
			"  and (:streetNumber is null or l.searchStreetNumber like upper(:streetNumber) escape '\\\\') \n " +
			"  and (:blockNumber is null or l.searchBlockNumber like upper(:blockNumber) escape '\\\\')\n " +
			"  and (:roomNumber is null or l.searchRoomNumber like upper(:roomNumber) escape '\\\\')")
	List<Location> list(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementPattern") String settlementNamePattern,
			@Param("streetKind") StreetKind streetKind,
			@Param("streetPattern") String streetNamePattern,
			@Param("postal") String postalCodePattern,
			@Param("streetNumber") String streetNumberPattern,
			@Param("blockNumber") String blockNumberPattern,
			@Param("roomNumber") String roomNumberPattern,
			Pageable pageable
	);

	@Query("select l from Location as l" +
			"\n where l.street.id = :streetId" +
			"\n   and l.searchStreetNumber = upper(:streetNumber)" +
			"\n   and ((:blockNumber is null and l.blockNumber is null) or (:blockNumber is not null and l.searchBlockNumber = upper(:blockNumber)))" +
			"\n   and ((:roomNumber is null and l.roomNumber is null) or (:roomNumber is not null and l.searchRoomNumber = upper(:roomNumber)))")
	Optional<Location> findByIdentity(
			@Param("streetId") Long streetId,
			@Param("streetNumber") String streetNumber,
			@Param("blockNumber") String blockNumber,
			@Param("roomNumber") String roomNumber
	);
}
