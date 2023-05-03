package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.commons.model.dto.BriefUserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Locale;

@Component
public class BriefUserInfoMapper implements LocalizedEntityMapper<User, BriefUserInfo, Long> {
	@Nonnull
	@Override
	public BriefUserInfo map(@Nonnull User entity, @Nonnull Locale locale) {
		final BriefUserInfo userInfo = new BriefUserInfo();
		userInfo.setId(entity.getId());
		userInfo.setLogin(entity.getLogin());
		userInfo.setEmail(entity.getEmail());
		userInfo.setFirstName(entity.getFirstName());
		userInfo.setLastName(entity.getLastName());
		return userInfo;
	}
}
