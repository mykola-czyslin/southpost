package ua.southpost.resource.backup.api.service.search;

import ua.southpost.resource.backup.api.model.UserSearchRequestPayload;
import ua.southpost.resource.commons.service.EntitySearchService;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static ua.southpost.resource.backup.web.utils.DataUtils.prepareOptionalPatternOrNullWhenEmpty;

@Service
class UserSearchService implements EntitySearchService<UserSearchRequestPayload, User, Long> {
	@Resource
	private UserRepository repository;

	@Override
	public long count(@Nonnull UserSearchRequestPayload searchPayload) {
		return repository.count(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getLoginPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getFirstNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getLastNamePattern()),
				searchPayload.getRegisteredAtFrom(),
				searchPayload.getRegisteredAtTo(),
				searchPayload.anyRoleAcceptable(),
				searchPayload.userRoles(),
				searchPayload.anyDeclaredActivityAcceptable(),
				searchPayload.userDeclaredActivities(),
				searchPayload.anyConfirmedActivityAcceptable(),
				searchPayload.userConfirmedActivities()
		);
	}

	@Override
	public List<User> list(@Nonnull UserSearchRequestPayload searchPayload, @Nonnull Pageable pageable) {
		return repository.list(
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getLoginPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getEmailPattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getFirstNamePattern()),
				prepareOptionalPatternOrNullWhenEmpty(searchPayload.getLastNamePattern()),
				searchPayload.getRegisteredAtFrom(),
				searchPayload.getRegisteredAtTo(),
				searchPayload.anyRoleAcceptable(),
				searchPayload.userRoles(),
				searchPayload.anyDeclaredActivityAcceptable(),
				searchPayload.userDeclaredActivities(),
				searchPayload.anyConfirmedActivityAcceptable(),
				searchPayload.userConfirmedActivities(),
				pageable
		);
	}
}
