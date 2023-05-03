package ua.southpost.resource.backup.data.repo;

import ua.southpost.resource.backup.data.model.LawCase;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.model.SettlementKind;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LawyerAgencyRepository extends JpaRepository<LawyerAgency, Long> {
	Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "searchValue");

	@Query("select count(a) from LawyerAgency as a\n" +
			"where (:regionId is null or a.settlement.district.region.id = :regionId)\n" +
			"  and (:districtId is null or a.settlement.district.id = :districtId) \n " +
			"  and (:settlementKind is null or a.settlement.kind = :settlementKind) \n " +
			"  and (:settlementNamePattern is null or a.settlement.searchValue like upper(:settlementNamePattern) escape '\\\\') \n " +
			"  and (:namePattern is null or a.searchValue like upper(:namePattern) escape '\\\\')\n" +
			"  and (:webSitePattern is null or upper(a.webSite) like upper(:webSitePattern) escape '\\\\')\n" +
			"  and (:phoneNumPattern is null or exists (select ph from a.phones as ph where ph.searchNumber like :phoneNumPattern escape '\\\\'))\n" +
			"  and (:emailPattern is null or exists (select em from a.emails as em where lower(em.emailAddress) like lower(:emailPattern) escape '\\\\'))\n" +
			"  and (:supportsAnyCase = true or exists (select cs from a.cases as cs where cs in :supportedCases))"
	)
	long count(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementNamePattern") String settlementNamePattern,
			@Param("namePattern") String namePattern,
			@Param("webSitePattern") String webSitePattern,
			@Param("phoneNumPattern") String phoneNumPattern,
			@Param("emailPattern") String emailPattern,
			@Param("supportsAnyCase") boolean anyCaseSupported,
			@Param("supportedCases") Set<LawCase> casesSupported
	);

	@Query(
			"select a from LawyerAgency as a\n" +
					"where (:regionId is null or a.settlement.district.region.id = :regionId)\n" +
					"  and (:districtId is null or a.settlement.district.id = :districtId) \n " +
					"  and (:settlementKind is null or a.settlement.kind = :settlementKind) \n " +
					"  and (:settlementNamePattern is null or a.settlement.searchValue like upper(:settlementNamePattern) escape '\\\\') \n " +
					"  and (:namePattern is null or a.searchValue like upper(:namePattern) escape '\\\\')\n" +
					"  and (:webSitePattern is null or upper(a.webSite) like upper(:webSitePattern) escape '\\\\')\n" +
					"  and (:phoneNumPattern is null or exists (select ph from a.phones as ph where ph.searchNumber like :phoneNumPattern escape '\\\\'))\n" +
					"  and (:emailPattern is null or exists (select em from a.emails as em where lower(em.emailAddress) like lower(:emailPattern) escape '\\\\'))\n" +
					"  and (:supportsAnyCase = true or exists (select cs from a.cases as cs where cs in :supportedCases))"
	)
	List<LawyerAgency> list(
			@Param("regionId") String regionId,
			@Param("districtId") Long districtId,
			@Param("settlementKind") SettlementKind settlementKind,
			@Param("settlementNamePattern") String settlementNamePattern,
			@Param("namePattern") String namePattern,
			@Param("webSitePattern") String webSitePattern,
			@Param("phoneNumPattern") String phoneNumPattern,
			@Param("emailPattern") String emailPattern,
			@Param("supportsAnyCase") boolean anyCaseSupported,
			@Param("supportedCases") Set<LawCase> casesSupported,
			Pageable pageable
	);

	@Query("select la from LawyerAgency as la\n where la.searchValue = upper(:agencyName)")
	Optional<LawyerAgency> findByName(@Param("agencyName") String searchValue);
}
