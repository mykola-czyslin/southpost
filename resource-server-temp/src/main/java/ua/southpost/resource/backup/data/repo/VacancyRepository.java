package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.NoYes;
import ua.southpost.resource.backup.data.model.SettlementKind;
import ua.southpost.resource.backup.data.model.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The repository interface definition for the {@link Vacancy} entity.
 * Created by mchys on 25.12.2018.
 */
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "searchValue");

	@Query("select count(vac) from Vacancy as vac \n" +
			"where (:summaryPattern is null or vac.searchValue like upper(:summaryPattern) escape '\\\\') \n " +
			"  and (:descriptionPattern is null or upper(vac.description) like upper(:descriptionPattern) escape '\\\\') \n " +
			"  and (:salary is null or ((vac.salaryHigh >= :salary)\n " +
			"   or (vac.salaryLow >= :salary)) ) \n" +
			"  and (:hosting is null or vac.hostingAvailable = :hosting)" +
			"  and (:employerPattern is null or vac.employer.searchValue like upper(:employerPattern) escape '\\\\') \n " +
			"  and (:settlementPattern is null or vac.employer.settlement.searchValue like upper(:settlementPattern) escape '\\\\') \n " +
			"  and (:settlementKind is null or vac.employer.settlement.kind = :settlementKind) \n" +
			"  and (:districtId is null or vac.employer.settlement.district.id = :districtId) \n" +
			"  and (:regionId is null or vac.employer.settlement.district.region.id = :regionId)\n " +
			"  and (:phonePattern is null or exists (select ph from vac.employer.phones as ph where ph.searchNumber like upper(:phonePattern) escape '\\\\'))\n " +
			"  and (:mailPattern is null or exists (select m from vac.employer.emails as m where m.emailAddress like lower(:mailPattern) ))")
	long count(
			@Param("summaryPattern") String summaryPattern,
			@Param("descriptionPattern") String descriptionPattern,
			@Param("salary") BigDecimal salary,
			@Param("hosting") NoYes hosting,
			@Param("employerPattern") String employerPattern,
			@Param("settlementPattern") String settlementPattern,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("districtId") Long districtId,
			@Param("regionId") String regionId,
			@Param("phonePattern") String phonePattern,
			@Param("mailPattern") String mailPattern
	);

	@Query("select vac from Vacancy as vac \n" +
			"where (:summaryPattern is null or vac.searchValue like upper(:summaryPattern) escape '\\\\') \n " +
			"  and (:descriptionPattern is null or upper(vac.description) like upper(:descriptionPattern) escape '\\\\') \n " +
			"  and (:salary is null or ((vac.salaryHigh >= :salary)\n " +
			"   or (vac.salaryLow >= :salary)) ) \n" +
			"  and (:hosting is null or vac.hostingAvailable = :hosting)" +
			"  and (:employerPattern is null or vac.employer.searchValue like upper(:employerPattern) escape '\\\\') \n " +
			"  and (:settlementPattern is null or vac.employer.settlement.searchValue like upper(:settlementPattern) escape '\\\\') \n " +
			"  and (:settlementKind is null or vac.employer.settlement.kind = :settlementKind) \n" +
			"  and (:districtId is null or vac.employer.settlement.district.id = :districtId) \n" +
			"  and (:regionId is null or vac.employer.settlement.district.region.id = :regionId)\n " +
			"  and (:phonePattern is null or exists (select ph from vac.employer.phones as ph where ph.searchNumber like upper(:phonePattern) escape '\\\\'))\n " +
			"  and (:mailPattern is null or exists (select m from vac.employer.emails as m where m.emailAddress like lower(:mailPattern) ))")
	List<Vacancy> list(
			@Param("summaryPattern") String summaryPattern,
			@Param("descriptionPattern") String descriptionPattern,
			@Param("salary") BigDecimal selary,
			@Param("hosting") NoYes hosting,
			@Param("employerPattern") String employerPattern,
			@Param("settlementPattern") String settlementPattern,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("districtId") Long districtId,
			@Param("regionId") String regionId,
			@Param("phonePattern") String phonePattern,
			@Param("mailPattern") String mailPattern,
			Pageable pageable
	);

	@Query("select vac from Vacancy as vac\n" +
					"where vac.employer.id = :employerId\n " +
					"and vac.searchValue = upper(:summary)"
	)
	Optional<Vacancy> findUnique(@Param("employerId") Long employerId, @Param("summary") String summary);
}
