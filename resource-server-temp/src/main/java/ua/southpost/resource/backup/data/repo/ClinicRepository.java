package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.model.ClinicType;
import ua.southpost.resource.backup.data.model.MedicalService;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.StreetKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "searchName");

	@Query("select count(c) from Clinic as c\n" +
			" left outer join c.locationAddress as l\n" +
			" left outer join l.street as str\n " +
			" left outer join str.settlement as stl\n " +
			" left outer join stl.district as dstr\n" +
			" left outer join dstr.region as rgn\n " +
			"where (:namePattern is null or c.searchName like upper(:namePattern) escape '\\\\') \n " +
			"  and (:clinicType is null or c.clinicType = :clinicType)\n" +
			"  and (:service is null or :service member of c.services)\n" +
			"  and (:descriptionPattern is null or upper(c.description) like upper(:descriptionPattern) escape '\\\\') \n " +
			"  and (:regionId is null or (rgn.id = :regionId))\n " +
			"  and (:districtId is null or (dstr.id = :districtId)) \n" +
			"  and (:settlementKind is null or (stl.kind = :settlementKind)) \n" +
			"  and (:settlementPattern is null or (stl.searchValue like upper(:settlementPattern) escape '\\\\')) \n " +
			"  and (:streetKind is null or l.street.kind = :streetKind)\n " +
			"  and (:streetPattern is null or l.street.searchValue like upper(:streetPattern) escape '\\\\')\n " +
			"  and (:postal is null or l.postalCode like :postal escape '\\\\')\n " +
			"  and (:streetNumber is null or l.searchStreetNumber like upper(:streetNumber) escape '\\\\') \n " +
			"  and (:blockNumber is null or l.searchBlockNumber like upper(:blockNumber) escape '\\\\')\n " +
			"  and (:roomNumber is null or l.searchRoomNumber like upper(:roomNumber) escape '\\\\')\n" +
			"  and (:phonePattern is null or exists (select ph from c.phones as ph where ph.searchNumber like upper(:phonePattern) escape '\\\\'))\n " +
			"  and (:mailPattern is null or exists (select m from c.emails as m where m.emailAddress like lower(:mailPattern) ))"
	)
	long count(
			@Param("namePattern") String namePattern,
			@Param("clinicType") ClinicType clinicType,
			@Param("service") MedicalService service,
			@Param("descriptionPattern") String descriptionPattern,
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementPattern") String settlementPattern,
			@Param("streetKind") StreetKind streetKind,
			@Param("streetPattern") String streetNamePattern,
			@Param("postal") String postalCodePattern,
			@Param("streetNumber") String streetNumberPattern,
			@Param("blockNumber") String blockNumberPattern,
			@Param("roomNumber") String roomNumberPattern,
			@Param("phonePattern") String phonePattern,
			@Param("mailPattern") String mailPattern
	);

	@Query("select c from Clinic as c where c.searchName = upper(:name)")
	Optional<Clinic> findByName(@Param("name") String name);

	@Query("select c from Clinic as c\n" +
			" left outer join c.locationAddress as l\n" +
			" left outer join l.street as str\n " +
			" left outer join str.settlement as stl\n " +
			" left outer join stl.district as dstr\n" +
			" left outer join dstr.region as rgn\n " +
			"where (:namePattern is null or c.searchName like upper(:namePattern) escape '\\\\') \n " +
			"  and (:clinicType is null or c.clinicType = :clinicType)\n" +
			"  and (:service is null or :service member of c.services)\n" +
			"  and (:descriptionPattern is null or upper(c.description) like upper(:descriptionPattern) escape '\\\\') \n " +
			"  and (:regionId is null or (rgn.id = :regionId))\n " +
			"  and (:districtId is null or (dstr.id = :districtId)) \n" +
			"  and (:settlementKind is null or (stl.kind = :settlementKind)) \n" +
			"  and (:settlementPattern is null or (stl.searchValue like upper(:settlementPattern) escape '\\\\')) \n " +
			"  and (:streetKind is null or l.street.kind = :streetKind)\n " +
			"  and (:streetPattern is null or l.street.searchValue like upper(:streetPattern) escape '\\\\')\n " +
			"  and (:postal is null or l.postalCode like :postal escape '\\\\')\n " +
			"  and (:streetNumber is null or l.searchStreetNumber like upper(:streetNumber) escape '\\\\') \n " +
			"  and (:blockNumber is null or l.searchBlockNumber like upper(:blockNumber) escape '\\\\')\n " +
			"  and (:roomNumber is null or l.searchRoomNumber like upper(:roomNumber) escape '\\\\')\n" +
			"  and (:phonePattern is null or exists (select ph from c.phones as ph where ph.searchNumber like upper(:phonePattern) escape '\\\\'))\n " +
			"  and (:mailPattern is null or exists (select m from c.emails as m where m.emailAddress like lower(:mailPattern) ))"
	)
	List<Clinic> list(
			@Param("namePattern") String namePattern,
			@Param("clinicType") ClinicType clinicType,
			@Param("service") MedicalService service,
			@Param("descriptionPattern") String descriptionPattern,
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementPattern") String settlementPattern,
			@Param("streetKind") StreetKind streetKind,
			@Param("streetPattern") String streetNamePattern,
			@Param("postal") String postalCodePattern,
			@Param("streetNumber") String streetNumberPattern,
			@Param("blockNumber") String blockNumberPattern,
			@Param("roomNumber") String roomNumberPattern,
			@Param("phonePattern") String phonePattern,
			@Param("mailPattern") String mailPattern,
			Pageable pageable
	);
}
