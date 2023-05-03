package ua.southpost.resource.backup.service.user;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.mail.GmailSender;
import ua.southpost.resource.backup.util.MessageBuilder;
import ua.southpost.resource.commons.model.dto.BriefUserInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Service
class UserPermissionRequestNotificationServiceImpl implements UserPermissionRequestNotificationService {
	static final int MAX_RECIPIENTS_NUMBER = 10;
	static final String SUBJECT_KEY = "mail.permission.required.notification.subject";
	static final String DEFAULT_SUBJECT_TEMPLATE = "Grant permission request for just registered user {0}";
	static final String MESSAGE_KEY = "mail.permission.required.notification.body";
	static final String DEFAULT_MESSAGE_TEMPLATE = "The user with ''{0}'' as login, ''{1}'' as first name, ''{2}'' as last name and ''{3}'' as e-mail just has been registered and needs to be granted with permissions respective to its kind set declared.\n\n" +
			"Mail is sent automatically no reply but your activity is required";
	@Resource
	private UserRepository userRepository;
	@Resource
	private MessageSource messageSource;
	@Resource
	private GmailSender gmailSender;
	@Value("${spring.mail.properties.system.address.from}")
	private String fromAddress;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void sendPermissionRequiredNotification(@Nonnull DetailedUserInfo user, @Nonnull Locale locale) {
		ofNullable(user.getDeclaredActivities()).orElseGet(Collections::emptySet)
				.stream()
				.filter(k -> k != null && k.getId() != UserActivityKind.VISITOR)
				.findFirst().ifPresent(k -> selectSupervisorsAndSendRequest(user, locale));
	}

	private void selectSupervisorsAndSendRequest(@Nonnull BriefUserInfo user, @Nonnull Locale locale) {
		final long supervisorCount = userRepository.countWithRole(UserRole.SUPERVISOR);
		logger.debug("There is {} supervisor(s) registered", supervisorCount);
		if (supervisorCount > 0) {
			final SimpleMailMessage message = new MessageBuilder()
					.withTo(collectRecipients())
					.withFrom(fromAddress)
					.withSubj(composeSubject(user, locale))
					.withText(composeMessageSource(user, locale))
					.build();

			logger.debug("Permission request mail message has been composed for user {}", user.getLogin());

			gmailSender.send(message);

			logger.debug("Permission request mail message has been for user {}", user);
		} else {
			logger.warn("There are't any supervisor registered, there is nobody whom system can send permission request for user {}", user.getLogin());
		}
	}

	@Nonnull
	private String composeSubject(@Nonnull BriefUserInfo user, @Nonnull Locale locale) {
		final Object[] templateParams = {user.getLogin()};
		final String defaultSubject = MessageFormat.format(DEFAULT_SUBJECT_TEMPLATE, templateParams);
		return defaultIfNull(messageSource.getMessage(SUBJECT_KEY, templateParams, defaultSubject, locale), defaultSubject);
	}

	@Nonnull
	private String composeMessageSource(@Nonnull BriefUserInfo requester, @Nonnull Locale locale) {
		final Object[] messageArguments = {requester.getLogin(), requester.getFirstName(), requester.getLastName(), requester.getEmail()};
		final String defaultMessage = MessageFormat.format(DEFAULT_MESSAGE_TEMPLATE, messageArguments);
		return defaultIfNull(messageSource.getMessage(MESSAGE_KEY, messageArguments, defaultMessage, locale), defaultMessage);
	}

	@Nonnull
	private List<String> collectRecipients() {
		return userRepository.listWithRole(UserRole.SUPERVISOR, PageRequest.of(0, MAX_RECIPIENTS_NUMBER, UserRepository.DEFAULT_SORT))
				.stream()
				.map(User::getEmail)
				.collect(Collectors.toList());
	}
}
