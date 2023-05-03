package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.SettlementSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Settlement;
import ua.southpost.resource.backup.data.repo.SettlementRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class SettlementSearchService implements EntitySearchService<SettlementSearchRequestPayload, Settlement, Long> {

	@Resource
	private SettlementRepository repository;

	@Override
	public long count(@Nonnull SettlementSearchRequestPayload searchPayload) {
		return repository.count(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), d -> d != null && d <= -1L),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern())
		);
	}

	@Override
	public List<Settlement> list(@Nonnull SettlementSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), d -> d != null && d <= -1L),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				pageable
		);
	}
}
