package ua.southpost.resource.backup.web.service.entity;

import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Optional;

public interface UserDataService extends EntityDataService<DetailedUserInfo, Long> {
	Optional<DetailedUserInfo> byLogin(@Nonnull String login, @Nonnull Locale locale);
}
