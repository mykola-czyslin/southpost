package ua.southpost.resource.backup.web.service.user;

import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;

import javax.annotation.Nonnull;
import java.util.Locale;

public interface UserRegistrationService {
	@Nonnull
	DetailedUserInfo register(@Nonnull UserRegistrationRequest request, Locale locale);
}
