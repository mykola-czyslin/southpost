package ua.southpost.resource.backup.web.controller.service.local;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.controller.service.UserProfilePayload;
import ua.southpost.resource.backup.web.controller.service.UserProfileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Optional.ofNullable;

@Service
class LocalUserProfileService implements UserProfileService {
	@Resource
	private UserRepository repository;

	@Override
	public void updateProfile(UserProfilePayload payload) {
		final User user = repository.findById(payload.getUserId())
				.orElseThrow(() -> new NotFoundException(NotFoundException.ERR_USER_NOT_FOUND_BY_ID, payload.getUserId()));
		ofNullable(payload.getLogin()).ifPresent(user::setLogin);
		ofNullable(payload.getEmail()).ifPresent(user::setEmail);
		ofNullable(payload.getFirstName()).ifPresent(user::setFirstName);
		ofNullable(payload.getLastName()).ifPresent(user::setLastName);
		ofNullable(payload.getUserActivities()).ifPresent(user::setDeclaredActivities);
		repository.save(user);
	}
}
