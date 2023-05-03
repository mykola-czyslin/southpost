package ua.southpost.resource.backup.service.user;

import com.google.common.collect.Sets;
import lombok.Builder;
import ua.southpost.resource.backup.data.model.MessageKeyHolder;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.service.mail.GmailSender;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
class PermissionChangedMessageSenderTask {
	static final String MAIL_SUBJECT_KEY = "mail.user.permission.change.notification.subject";
	static final String MAIL_USER_GREETING_KEY = "mail.user.greeting";
	static final String MAIL_USER_PREFACE_KEY = "mail.user.preface";
	static final String MAIL_USER_PERMISSION_ROLES_REVOKED_KEY = "mail.user.permission.roles.revoked";
	static final String MAIL_USER_PERMISSION_ROLES_JUST_GRANTED_KEY = "mail.user.permission.roles.just.granted";
	static final String MAIL_USER_PERMISSIONS_DENIED_ACTIVITIES_KEY = "mail.user.permissions.denied.activities";
	static final String MAIL_USER_PERMISSIONS_APPROVED_ACTIVITIES_KEY = "mail.user.permissions.approved.activities";
	static final String MAIL_USER_PERMISSION_SUMMARY_KEY = "mail.user.permission.summary";
	static final String MAIL_NO_REPLY_BEST_REGARDS_KEY = "mail.no.reply.best.regards";
	private final MessageSource messageSource;
	private final GmailSender gmailSender;
	private final String siteAddress;
	private final String fromAddress;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String login;
	private final Set<UserRole> alreadyGrantedRoles;
	private final Set<UserRole> actualRoles;
	private final Set<UserActivityKind> alreadyConfirmedActivities;
	private final Set<UserActivityKind> actualConfirmedActivities;
	private final Locale locale;

	void execute() {
		String messageBody = composeMessageBody();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setText(messageBody);
		mailMessage.setSubject(messageSource.getMessage(MAIL_SUBJECT_KEY, null, locale));
		mailMessage.setFrom(fromAddress);
		mailMessage.setTo(email);
		gmailSender.send(mailMessage);
	}

	private String composeMessageBody() {
		return greeting() + "\n"
				+ preface() + "\n"
				+ revokedRoles() + "\n"
				+ grantedRoles() + "\n"
				+ deniedActivities() + "\n"
				+ approvedActivities() + "\n\n"
				+ summary() + "\n\n"
				+ wbr();
	}

	private String greeting() {
		// "hello {0} {1}!"
		return messageSource.getMessage(MAIL_USER_GREETING_KEY, new Object[] {firstName, lastName}, locale);
	}

	private String preface() {
		//"You are registered as {0} on a site {1}"
		return messageSource.getMessage(MAIL_USER_PREFACE_KEY, new Object[]{login, siteAddress}, locale);
	}

	private String revokedRoles() {
		List<String> revokedRoles = Sets.difference(alreadyGrantedRoles, actualRoles)
				.stream()
				.sorted()
				.map(this::mapMessageKeyHolder)
				.collect(Collectors.toList());
		// "The following roles were revoked from you: {0}."
		return revokedRoles.isEmpty() ? "" : messageSource.getMessage(MAIL_USER_PERMISSION_ROLES_REVOKED_KEY, new Object[] { revokedRoles }, locale);
	}

	private String grantedRoles() {
		List<String> grantedRoles = Sets.difference(actualRoles, alreadyGrantedRoles)
				.stream()
				.sorted()
				.map(this::mapMessageKeyHolder)
				.collect(Collectors.toList());
		// "The following roles have been granted to you just now: {0}"
		return grantedRoles.isEmpty() ? "" : messageSource.getMessage(MAIL_USER_PERMISSION_ROLES_JUST_GRANTED_KEY, new Object[] { grantedRoles }, locale);
	}

	private String deniedActivities() {
		List<String> denied
				= Sets.difference(alreadyConfirmedActivities, actualConfirmedActivities)
				.stream()
				.sorted()
				.map(this::mapMessageKeyHolder)
				.collect(Collectors.toList());
		// "You have been denied from the following activities: {0}."
		return denied.isEmpty() ? "" : messageSource.getMessage(MAIL_USER_PERMISSIONS_DENIED_ACTIVITIES_KEY, new Object[]{ denied }, locale);
	}

	private String approvedActivities() {
		List<String> approved
				= Sets.difference(actualConfirmedActivities, alreadyConfirmedActivities)
				.stream()
				.sorted()
				.map(this::mapMessageKeyHolder)
				.collect(Collectors.toList());
		// "The following activities have been added to your approved ones: {0}"
		return approved.isEmpty() ? "" : messageSource.getMessage(MAIL_USER_PERMISSIONS_APPROVED_ACTIVITIES_KEY, new Object[]{ approved }, locale);
	}

	private String summary() {
		List<String> currentRoles = actualRoles.stream()
				.sorted()
				.map(this::mapMessageKeyHolder)
				.collect(Collectors.toList());
		List<String> currentActivities = actualConfirmedActivities
				.stream()
				.sorted()
				.map(this::mapMessageKeyHolder)
				.collect(Collectors.toList());
		// "Currently you are granted with following roles: {0}. Also you're approved to perform following activities: {1}"
		return messageSource.getMessage(MAIL_USER_PERMISSION_SUMMARY_KEY, new Object[]{currentRoles, currentActivities}, locale);
	}

	private String wbr() {
		// "No reply on this message required. With best regards, support team"
		return messageSource.getMessage(MAIL_NO_REPLY_BEST_REGARDS_KEY, null, locale);
	}

	private String mapMessageKeyHolder(MessageKeyHolder keyHolder) {
		return messageSource.getMessage(keyHolder.getMessageKey(), null, locale);
	}
}
