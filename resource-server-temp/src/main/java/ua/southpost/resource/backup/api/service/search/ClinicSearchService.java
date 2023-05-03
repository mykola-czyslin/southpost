package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.ClinicSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Clinic;
import ua.southpost.resource.backup.data.repo.ClinicRepository;
import ua.southpost.resource.backup.web.utils.DataUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.util.OptionsUtilConstants.NULL_OPTION_VALUE;
import static ua.southpost.resource.backup.web.utils.DataUtils.nullWhenMatch;
import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class ClinicSearchService implements EntitySearchService<ClinicSearchRequestPayload, Clinic, Long> {
	@Resource
	private ClinicRepository repository;

	@Override
	public long count(@Nonnull ClinicSearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getNamePattern()),
				searchPayload.getClinicType(),
				searchPayload.getService(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern()),
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				DataUtils.<Long>nullWhenMatch(searchPayload.getDistrictId(), did -> did != null && did <= 0L),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getStreetKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPostalCodePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getBlockNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getRoomNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhonePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailPattern())
		);
	}

	@Override
	public List<Clinic> list(@Nonnull ClinicSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getNamePattern()),
				searchPayload.getClinicType(),
				searchPayload.getService(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern()),
				nullWhenMatch(searchPayload.getRegionId(), NULL_OPTION_VALUE::equalsIgnoreCase),
				DataUtils.<Long>nullWhenMatch(searchPayload.getDistrictId(), did -> did != null && did <= 0L),
				searchPayload.getSettlementKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getSettlementNamePattern()),
				searchPayload.getStreetKind(),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPostalCodePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getStreetNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getBlockNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getRoomNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhonePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailPattern()),
				pageable
		);
	}
}
