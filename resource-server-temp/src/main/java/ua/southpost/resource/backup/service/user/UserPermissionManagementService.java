package ua.southpost.resource.backup.service.user;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Set;

public interface UserPermissionManagementService {
	@Transactional(propagation = Propagation.SUPPORTS)
	void updateUserPermissions(long userId, @Nonnull Set<UserRole> roles, @Nonnull Set<UserActivityKind> activities, @Nonnull Locale locale);
}
