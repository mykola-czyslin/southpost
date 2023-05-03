package ua.southpost.resource.backup.service.user;

import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;

import java.util.Locale;

public interface UserPermissionRequestNotificationService {
	void sendPermissionRequiredNotification(DetailedUserInfo user, Locale locale);
}
