package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.StreetSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Street;
import ua.southpost.resource.backup.data.repo.StreetRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class StreetSearchService implements EntitySearchService<StreetSearchRequestPayload, Street, Long> {
	@Resource
	private StreetRepository repository;

	@Override
	public long count(@Nonnull StreetSearchRequestPayload searchPayload) {
		return repository.count(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), d -> (d != null && d <= 0)),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getStreetKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNamePattern())
		);
	}

	@Override
	public List<Street> list(@Nonnull StreetSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), d -> (d != null && d <= 0)),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getStreetKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNamePattern()),
				pageable
		);
	}
}
