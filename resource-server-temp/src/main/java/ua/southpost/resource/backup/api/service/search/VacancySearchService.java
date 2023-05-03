package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.VacancySearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Vacancy;
import ua.southpost.resource.backup.data.repo.VacancyRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class VacancySearchService implements EntitySearchService<VacancySearchRequestPayload, Vacancy, Long> {
	@Resource
	private VacancyRepository repository;

	@Override
	public long count(@Nonnull VacancySearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSummaryPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern()),
				searchPayload.getSalaryLow(),
				searchPayload.getHosting(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmployerNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getSettlementKind(),
				nullWhenMatch(searchPayload.getDistrictId(), id -> id != null && id <= 0L),
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhonePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getMailPattern())
		);
	}

	@Override
	public List<Vacancy> list(@Nonnull VacancySearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSummaryPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern()),
				searchPayload.getSalaryLow(),
				searchPayload.getHosting(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmployerNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getSettlementKind(),
				nullWhenMatch(searchPayload.getDistrictId(), id -> id != null && id <= 0L),
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhonePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getMailPattern()),
				pageable
		);
	}
}
