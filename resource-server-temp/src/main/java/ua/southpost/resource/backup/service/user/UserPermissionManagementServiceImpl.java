package ua.southpost.resource.backup.service.user;

import com.google.common.collect.Sets;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.service.mail.GmailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
class UserPermissionManagementServiceImpl implements UserPermissionManagementService {
	@Resource
	private UserRepository repository;
	@Resource
	private MessageSource messageSource;
	@Resource
	private GmailSender gmailSender;
	@Value("${spring.mail.properties.system.address.from}")
	private String fromAddress;
	@Value("${spring.application.properties.site.address}")
	private String siteAddress;

	@Override
	public void updateUserPermissions(long userId, @Nonnull Set<UserRole> roles, @Nonnull Set<UserActivityKind> activities, @Nonnull Locale locale) {
		User user = repository.findById(userId)
				.orElseThrow(() -> new NotFoundException(NotFoundException.ERR_USER_NOT_FOUND_BY_ID, userId));
		Set<UserActivityKind> alreadyConfirmedActivities = Sets.newHashSet(ofNullable(user.getConfirmedActivities()).orElseGet(Collections::emptySet));
		Set<UserRole> alreadyGrantedRoles = Sets.newHashSet(ofNullable(user.getRoles()).orElseGet(Collections::emptySet));
		user.setConfirmedActivities(activities);
		user.setRoles(roles);
		user = repository.save(user);
		PermissionChangedMessageSenderTask.builder()
				.messageSource(messageSource)
				.gmailSender(gmailSender)
				.fromAddress(fromAddress)
				.siteAddress(siteAddress)
				.actualRoles(roles)
				.actualConfirmedActivities(activities)
				.alreadyGrantedRoles(alreadyGrantedRoles)
				.alreadyConfirmedActivities(alreadyConfirmedActivities)
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.login(user.getLogin())
				.email(user.getEmail())
				.locale(locale)
				.build()
				.execute();
	}

}
