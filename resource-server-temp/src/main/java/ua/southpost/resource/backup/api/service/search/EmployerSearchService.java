package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.EmployerSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Employer;
import ua.southpost.resource.backup.data.repo.EmployerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.service.RegionAndDistrictOptionsContainer.MOCK_REGION_ID;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class EmployerSearchService implements EntitySearchService<EmployerSearchRequestPayload, Employer, Long> {

	@Resource
	private EmployerRepository repository;

	@Override
	public long count(@Nonnull EmployerSearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmployerNamePattern()),
				searchPayload.isWebSiteSignificant() ? 1 : 0,
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getWebSiteAddressPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getSettlementKind(),
				nullWhenMatch(searchPayload.getDistrictId(), d -> (d != null && -1L == d)),
				nullWhenMatch(searchPayload.getRegionId(), MOCK_REGION_ID::equalsIgnoreCase)
		);
	}

	@Override
	public List<Employer> list(@Nonnull EmployerSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmployerNamePattern()),
				searchPayload.isWebSiteSignificant() ? 1 : 0,
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getWebSiteAddressPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getSettlementKind(),
				nullWhenMatch(searchPayload.getDistrictId(), d -> (d != null && -1L == d)),
				nullWhenMatch(searchPayload.getRegionId(), MOCK_REGION_ID::equalsIgnoreCase),
				pageable
		);
	}
}
