package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.LocationSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Location;
import ua.southpost.resource.backup.data.repo.LocationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class LocationSearchService implements EntitySearchService<LocationSearchRequestPayload, Location, Long> {

	@Resource
	private LocationRepository repository;

	@Override
	public long count(@Nonnull LocationSearchRequestPayload searchPayload) {
		return repository.count(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), aLong -> aLong != null && aLong <= 0L),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getStreetKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPostalCodePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getBlockNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getRoomNumberPattern())
		);
	}

	@Override
	public List<Location> list(@Nonnull LocationSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), aLong -> aLong != null && aLong <= 0L),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getStreetKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPostalCodePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getBlockNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getRoomNumberPattern()),
				pageable
		);
	}
}
