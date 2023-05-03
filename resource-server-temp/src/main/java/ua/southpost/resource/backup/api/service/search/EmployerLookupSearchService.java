package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.EmployerLookupRequest;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.repo.EmployerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

@Service
class EmployerLookupSearchService implements EntitySearchService<EmployerLookupRequest, Employer, Long> {
	@Resource
	private EmployerRepository repository;

	@Override
	public long count(@Nonnull EmployerLookupRequest searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmployerName()),
				isNotBlank(searchPayload.getWebSite()) ? 1 : 0,
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getWebSite()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementName()),
				searchPayload.getSettlementKind(),
				nullWhenMatch(searchPayload.getDistrictId(), d -> d != null && d <= 0),
				nullWhenMatch(searchPayload.getRegionId(), s -> isBlank(s) || NULL_OPTION_VALUE.equals(trim(s)))
		);
	}

	@Override
	public List<Employer> list(@Nonnull EmployerLookupRequest searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmployerName()),
				isNotBlank(searchPayload.getWebSite()) ? 1 : 0,
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getWebSite()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementName()),
				searchPayload.getSettlementKind(),
				nullWhenMatch(searchPayload.getDistrictId(), d -> d != null && d <= 0),
				nullWhenMatch(searchPayload.getRegionId(), s -> isBlank(s) || NULL_OPTION_VALUE.equals(trim(s))),
				pageable
		);
	}
}
