package ua.southpost.resource.backup.web.controller.service.local;

import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.controller.service.UserCredentialsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
class LocalUserCredentialsService implements UserCredentialsService {
	@Resource
	private UserRepository repository;
	@Resource(name = "passwordEncoder")
	private PasswordEncoder encoder;

	@Override
	public void updateCredentials(long userId, String oldPassword, String newPassword) {
		final String oldEncoded = encoder.encode(oldPassword);
		final String newEncoded = encoder.encode(newPassword);
		repository.findById(userId)
				.filter(u -> StringUtils.equals(u.getPassword(), oldEncoded))
				.map(u -> { u.setPassword(newEncoded); return repository.save(u); })
				.orElseThrow(() -> new NotFoundException(NotFoundException.ERR_USER_NOT_FOUND_BY_ID, userId));
	}
}
