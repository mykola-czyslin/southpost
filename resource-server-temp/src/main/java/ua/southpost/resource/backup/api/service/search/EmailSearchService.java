package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.search.EmailSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.EmailAddress;
import ua.southpost.resource.backup.data.repo.EmailAddressRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class EmailSearchService implements EntitySearchService<EmailSearchRequestPayload, EmailAddress, Long> {
	@Resource
	private EmailAddressRepository repository;

	@Override
	public long count(@Nonnull EmailSearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailAddressPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern())
		);
	}

	@Override
	public List<EmailAddress> list(@Nonnull EmailSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailAddressPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getDescriptionPattern()),
				pageable
		);
	}
}
