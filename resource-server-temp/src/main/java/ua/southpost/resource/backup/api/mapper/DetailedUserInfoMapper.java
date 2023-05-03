package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.MessageKeyHolder;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
class DetailedUserInfoMapper implements LocalizedEntityMapper<User, DetailedUserInfo, Long> {
	@Resource
	private MessageKeyHolderLocalizedMapper keyHolderMapper;

	@Nonnull
	@Override
	public DetailedUserInfo map(@Nonnull User entity, @Nonnull Locale locale) {
		final DetailedUserInfo userInfo = new DetailedUserInfo();
		userInfo.setId(entity.getId());
		userInfo.setLogin(entity.getLogin());
		userInfo.setEmail(entity.getEmail());
		userInfo.setFirstName(entity.getFirstName());
		userInfo.setLastName(entity.getLastName());
		userInfo.setPassword(entity.getPassword());
		userInfo.setRoles(mapKeyHolders(entity.getRoles(), locale));
		userInfo.setDeclaredActivities(mapKeyHolders(entity.getDeclaredActivities(), locale));
		userInfo.setConfirmedActivities(mapKeyHolders(entity.getConfirmedActivities(), locale));
		userInfo.setRegisteredAt(entity.getRegisteredAt());
		userInfo.setInternal(entity.isInternalUser());
		return userInfo;
	}

	@Nonnull
	<T extends MessageKeyHolder> Set<AbstractEntityInfo<T>> mapKeyHolders(Set<T> keyHolders, @Nonnull Locale locale) {
		return ofNullable(keyHolders).orElseGet(Collections::emptySet).stream().map(r -> keyHolderMapper.map(r, locale)).collect(Collectors.toSet());
	}
}
