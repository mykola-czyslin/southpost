package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.LawyerAgencySearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.LawyerAgency;
import ua.southpost.resource.backup.data.repo.LawyerAgencyRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;
import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Service
class LawyerAgencySearchService implements EntitySearchService<LawyerAgencySearchRequestPayload, LawyerAgency, Long> {

	@Resource
	private LawyerAgencyRepository repository;

	@Override
	public long count(@Nonnull LawyerAgencySearchRequestPayload searchPayload) {
		return repository.count(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), id -> Long.valueOf(-1L).equals(id)),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getAgencyNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getWebSitePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhoneNumPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailPattern()),
				isEmpty(searchPayload.getCases()),
				ofNullable(searchPayload.getCases()).orElseGet(Collections::emptySet)
		);
	}

	@Override
	public List<LawyerAgency> list(@Nonnull LawyerAgencySearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), id -> Long.valueOf(-1L).equals(id)),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getAgencyNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getWebSitePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhoneNumPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailPattern()),
				isEmpty(searchPayload.getCases()),
				ofNullable(searchPayload.getCases()).orElseGet(Collections::emptySet),
				pageable
		);
	}

}
