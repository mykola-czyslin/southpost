package ua.southpost.resource.backup.web.service.user;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import static com.google.common.collect.ImmutableSet.of;
import static java.util.Optional.ofNullable;
import static ua.southpost.resource.backup.data.model.UserActivityKind.VISITOR;

@Service
class LocalRegistrationService implements UserRegistrationService {
	@Resource
	private UserRepository repository;
	@Resource(name = "passwordEncoder")
	private PasswordEncoder encoder;
	@Resource(name = "detailedUserInfoMapper")
	private LocalizedEntityMapper<User, DetailedUserInfo, Long> userInfoMapper;

	@Nonnull
	@Override
	public DetailedUserInfo register(@Nonnull UserRegistrationRequest request, Locale locale) {
		final User user = new User();
		user.setEmail(request.getEmail());
		user.setLogin(request.getLogin());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setRoles(Collections.singleton(UserRole.VIEWER));
		user.setDeclaredActivities(ofNullable(request.getDeclaredActivities()).orElseGet(() -> of(VISITOR)));
		user.setRegisteredAt(new Date());
		return userInfoMapper.map(repository.save(user), locale);
	}
}
