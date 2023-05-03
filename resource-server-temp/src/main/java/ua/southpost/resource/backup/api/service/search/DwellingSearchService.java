package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.DwellingSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Dwelling;
import ua.southpost.resource.backup.data.repo.DwellingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class DwellingSearchService implements EntitySearchService<DwellingSearchRequestPayload, Dwelling, Long> {

	@Resource
	private DwellingRepository repository;

	@Override
	public long count(@Nonnull DwellingSearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementAreaPattern()),
				searchPayload.getDwellingKind(),
				nullWhenMatch(searchPayload.getNumberOfRoomsFrom(), n -> n != null && n <= 0),
				nullWhenMatch(searchPayload.getNumberOfRoomsTo(), n -> n != null && n <= 0),
				nullWhenMatch(searchPayload.getTotalAreaFrom(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				nullWhenMatch(searchPayload.getTotalAreaTo(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				nullWhenMatch(searchPayload.getLivingAreaFrom(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				nullWhenMatch(searchPayload.getLivingAreaTo(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				searchPayload.getBillingPeriod(),
				nullWhenMatch(searchPayload.getPriceFrom(), p -> p != null && BigDecimal.ZERO.compareTo(p) >= 0),
				nullWhenMatch(searchPayload.getPriceTo(), p -> p != null && BigDecimal.ZERO.compareTo(p) >= 0),
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), id -> Long.valueOf(-1L).equals(id)),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getContactPhonePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getContactEmailPattern())
		);
	}

	@Override
	public List<Dwelling> list(@Nonnull DwellingSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementAreaPattern()),
				searchPayload.getDwellingKind(),
				nullWhenMatch(searchPayload.getNumberOfRoomsFrom(), n -> n != null && n <= 0),
				nullWhenMatch(searchPayload.getNumberOfRoomsTo(), n -> n != null && n <= 0),
				nullWhenMatch(searchPayload.getTotalAreaFrom(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				nullWhenMatch(searchPayload.getTotalAreaTo(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				nullWhenMatch(searchPayload.getLivingAreaFrom(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				nullWhenMatch(searchPayload.getLivingAreaTo(), a -> a != null && BigDecimal.ZERO.compareTo(a) >= 0),
				searchPayload.getBillingPeriod(),
				nullWhenMatch(searchPayload.getPriceFrom(), p -> p != null && BigDecimal.ZERO.compareTo(p) >= 0),
				nullWhenMatch(searchPayload.getPriceTo(), p -> p != null && BigDecimal.ZERO.compareTo(p) >= 0),
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				nullWhenMatch(searchPayload.getDistrictId(), id -> Long.valueOf(-1L).equals(id)),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getContactPhonePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getContactEmailPattern()),
				pageable
		);
	}
}
