package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.PhoneSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.Phone;
import ua.southpost.resource.backup.data.repo.PhoneRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class PhoneSearchService implements EntitySearchService<PhoneSearchRequestPayload, Phone, Long> {
	@Resource
	private PhoneRepository repository;
	@Override
	public long count(@Nonnull PhoneSearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhoneNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern())
		);
	}

	@Override
	public List<Phone> list(@Nonnull PhoneSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getPhoneNumberPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern()),
				pageable
		);
	}
}
