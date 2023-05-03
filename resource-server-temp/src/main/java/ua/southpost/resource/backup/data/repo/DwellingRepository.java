package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.BillingPeriod;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.model.DwellingKind;
import ua.southpost.resource.backup.data.model.SettlementKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The repository interface definition for the {@link Dwelling} entity.
 * Created by mchys on 25.12.2018.
 */
public interface DwellingRepository extends JpaRepository<Dwelling, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "price");

	@Query("select count(d) from Dwelling as d \n " +
			"where (:regionId is null or d.settlement.district.region.id = :regionId) \n" +
			"  and (:districtId is null or d.settlement.district.id = :districtId) \n " +
			"  and (:settlementKind is null or d.settlement.kind = :settlementKind) \n " +
			"  and (:settlementNamePattern is null or d.settlement.searchValue like upper(:settlementNamePattern) escape '\\\\') \n " +
			"  and (:settlementAreaPattern is null or d.settlementAreaSearch like upper(:settlementAreaPattern) escape '\\\\') \n " +
			"  and (:dwellingKind is null or d.kind = :dwellingKind) \n " +
			"  and (:numberOfRoomsFrom is null or d.numberOfRooms >= :numberOfRoomsFrom) \n " +
			"  and (:numberOfRoomsTo is null or d.numberOfRooms <= :numberOfRoomsTo) \n " +
			"  and (:totalAreaFrom is null or d.totalArea >= :totalAreaFrom) \n " +
			"  and (:totalAreaTo is null or d.totalArea <= :totalAreaTo) \n" +
			"  and (:livingAreaFrom is null or d.livingArea >= :livingAreaFrom) \n" +
			"  and (:livingAreaTo is null or d.livingArea <= :livingAreaTo) \n" +
			"  and (:contactPhonePattern is null or exists(select ph from d.phones as ph where ph.searchNumber like upper(:contactPhonePattern) escape '\\\\'))\n" +
			"  and (:contactEmailPattern is null or exists(select em from d.emails as em where em.emailAddress like lower(:contactEmailPattern) escape '\\\\'))\n" +
			"  and (:billingPeriod is null or d.billingPeriod = :billingPeriod) \n " +
			"  and (:priceFrom is null or d.price >= :priceFrom)\n " +
			"  and (:priceTo is null or d.price <= :priceTo)")
	long count(
			@Param("settlementAreaPattern") String settlementAreaPattern,
			@Param("dwellingKind") DwellingKind dwellingKind,
			@Param("numberOfRoomsFrom") Integer numberOfRoomsFrom,
			@Param("numberOfRoomsTo") Integer numberOfRoomsTo,
			@Param("totalAreaFrom") BigDecimal totalAreaFrom,
			@Param("totalAreaTo") BigDecimal totalAreaTo,
			@Param("livingAreaFrom") BigDecimal livingAreaFrom,
			@Param("livingAreaTo") BigDecimal livingAreaTo,
			@Param("billingPeriod") BillingPeriod billingPeriod,
			@Param("priceFrom") BigDecimal priceFrom,
			@Param("priceTo") BigDecimal priceTo,
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementNamePattern") String settlementNamePattern,
			@Param("contactPhonePattern") String phonePattern,
			@Param("contactEmailPattern") String emailPattern
	);


	@Query("select d from Dwelling as d \n " +
			"where (:regionId is null or d.settlement.district.region.id = :regionId) \n" +
			"  and (:districtId is null or d.settlement.district.id = :districtId) \n " +
			"  and (:settlementKind is null or d.settlement.kind = :settlementKind) \n " +
			"  and (:settlementNamePattern is null or d.settlement.searchValue like upper(:settlementNamePattern) escape '\\\\') \n " +
			"  and (:settlementAreaPattern is null or d.settlementAreaSearch like upper(:settlementAreaPattern) escape '\\\\') \n " +
			"  and (:dwellingKind is null or d.kind = :dwellingKind) \n " +
			"  and (:numberOfRoomsFrom is null or d.numberOfRooms >= :numberOfRoomsFrom) \n " +
			"  and (:numberOfRoomsTo is null or d.numberOfRooms <= :numberOfRoomsTo) \n " +
			"  and (:totalAreaFrom is null or d.totalArea >= :totalAreaFrom) \n " +
			"  and (:totalAreaTo is null or d.totalArea <= :totalAreaTo) \n" +
			"  and (:livingAreaFrom is null or d.livingArea >= :livingAreaFrom) \n" +
			"  and (:livingAreaTo is null or d.livingArea <= :livingAreaTo) \n" +
			"  and (:contactPhonePattern is null or exists(select ph from d.phones as ph where ph.searchNumber like upper(:contactPhonePattern) escape '\\\\'))\n" +
			"  and (:contactEmailPattern is null or exists(select em from d.emails as em where em.emailAddress like lower(:contactEmailPattern) escape '\\\\'))\n" +
			"  and (:billingPeriod is null or d.billingPeriod = :billingPeriod) \n " +
			"  and (:priceFrom is null or d.price >= :priceFrom)\n " +
			"  and (:priceTo is null or d.price <= :priceTo)")
	List<Dwelling> list(
			@Param("settlementAreaPattern") String settlementAreaPattern,
			@Param("dwellingKind") DwellingKind dwellingKind,
			@Param("numberOfRoomsFrom") Integer numberOfRoomsFrom,
			@Param("numberOfRoomsTo") Integer numberOfRoomsTo,
			@Param("totalAreaFrom") BigDecimal totalAreaFrom,
			@Param("totalAreaTo") BigDecimal totalAreaTo,
			@Param("livingAreaFrom") BigDecimal livingAreaFrom,
			@Param("livingAreaTo") BigDecimal livingAreaTo,
			@Param("billingPeriod") BillingPeriod billingPeriod,
			@Param("priceFrom") BigDecimal priceFrom,
			@Param("priceTo") BigDecimal priceTo,
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementNamePattern") String settlementNamePattern,
			@Param("contactPhonePattern") String phonePattern,
			@Param("contactEmailPattern") String emailPattern,
			Pageable pageable
	);

	@Query("select dwl from Dwelling as dwl\n" +
			"left join dwl.locationAddress as addr\n " +
			"where dwl.settlement.id = :settlementId\n" +
			"  and dwl.settlementAreaSearch = upper(:settlementArea)\n" +
			"  and dwl.kind = :kind\n" +
			"  and addr.id = :locationId")
	Optional<Dwelling> findUnique(
			@Param("settlementId") Long settlementId,
			@Param("settlementArea") String settlementArea,
			@Param("kind") DwellingKind dwellingKind,
			@Param("locationId") Long locationId
	);

}
