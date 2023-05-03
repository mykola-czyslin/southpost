package ua.southpost.resource.backup.web.service.entity;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Locale;
import java.util.Optional;


@Service
class LocalUserDataService implements UserDataService {
	@Resource
	private UserRepository repository;
	@Resource(name = "detailedUserInfoMapper")
	private LocalizedEntityMapper<User, DetailedUserInfo, Long> entityInfoMapper;

	@Override
	public Optional<DetailedUserInfo> byLogin(@Nonnull String login, @Nonnull Locale locale) {
		return repository.findByLogin(login).map(identity -> entityInfoMapper.map(identity, locale));
	}

	@Override
	public Optional<DetailedUserInfo> byId(@Nonnull Long id, Locale locale) {
		return repository.findById(id).map(identity -> entityInfoMapper.map(identity, locale));
	}
}
