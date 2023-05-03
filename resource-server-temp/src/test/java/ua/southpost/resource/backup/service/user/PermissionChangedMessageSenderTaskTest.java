package ua.southpost.resource.backup.service.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.service.mail.GmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.EnumSet.of;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_NO_REPLY_BEST_REGARDS_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_SUBJECT_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_GREETING_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_PERMISSIONS_APPROVED_ACTIVITIES_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_PERMISSIONS_DENIED_ACTIVITIES_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_PERMISSION_ROLES_JUST_GRANTED_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_PERMISSION_ROLES_REVOKED_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_PERMISSION_SUMMARY_KEY;
import static ua.southpost.resource.backup.service.user.PermissionChangedMessageSenderTask.MAIL_USER_PREFACE_KEY;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PermissionChangedMessageSenderTaskTest {
	static final String MAIL_SUBJECT = "Mail subject";
	static final String GREETING_TEMPLATE = "Hello {0} {1}!";
	static final String PREFACE_TEMPLATE = "You are registered as {0} on a site {1}.";
	static final String ROLES_REVOKED_TEMPLATE = "The following roles were revoked from you: {0}.";
	static final String ROLES_GRANTED_TEMPLATE = "The following roles have been granted to you just now: {0}";
	static final String DENIED_ACTIVITIES_TEMPLATE = "You have been denied from the following activities: {0}.";
	static final String APPROVED_ACTIVITIES_TEMPLATE = "The following activities have been added to your approved ones: {0}.";
	static final String SUMMARY_TEMPLATE = "Currently you are granted with following roles: {0}. Also you're approved to perform following activities: {1}.";
	static final String BEST_REGARDS = "No reply on this message required. With best regards, support team.";
	static final Locale DEFAULT_LOCALE = Locale.getDefault();

	static final String FROM_ADDRESS = "from@address.com";
	static final String SITE_ADDRESS = "https://site-address.com/";
	static final String FIRST_NAME = "John";
	static final String LAST_NAME = "Doe";
	static final String LOGIN = "johndoe";
	static final String EMAIL = "john.doe@email.com";

	private static final Map<String, String> KEY_TEMPLATE_MAP;

	static {
		Map<String, String> temp = Maps.newConcurrentMap();
		temp.put(MAIL_SUBJECT_KEY, MAIL_SUBJECT);
		temp.put(MAIL_USER_GREETING_KEY, GREETING_TEMPLATE);
		temp.put(MAIL_USER_PREFACE_KEY, PREFACE_TEMPLATE);
		temp.put(MAIL_USER_PERMISSION_ROLES_REVOKED_KEY, ROLES_REVOKED_TEMPLATE);
		temp.put(MAIL_USER_PERMISSION_ROLES_JUST_GRANTED_KEY, ROLES_GRANTED_TEMPLATE);
		temp.put(MAIL_USER_PERMISSIONS_DENIED_ACTIVITIES_KEY, DENIED_ACTIVITIES_TEMPLATE);
		temp.put(MAIL_USER_PERMISSIONS_APPROVED_ACTIVITIES_KEY, APPROVED_ACTIVITIES_TEMPLATE);
		temp.put(MAIL_USER_PERMISSION_SUMMARY_KEY, SUMMARY_TEMPLATE);
		temp.put(MAIL_NO_REPLY_BEST_REGARDS_KEY, BEST_REGARDS);
		temp.putAll(Arrays.stream(UserRole.values()).collect(Collectors.toMap(UserRole::getMessageKey, UserRole::name)));
		temp.putAll(Arrays.stream(UserActivityKind.values()).collect(Collectors.toMap(UserActivityKind::getMessageKey, UserActivityKind::name)));
		KEY_TEMPLATE_MAP = Collections.unmodifiableMap(temp);
	}


	@Mock
	private GmailSender gmailSenderMock;
	@Mock
	private MessageSource messageSourceMock;


	@Before
	public void setUp() {
		when(messageSourceMock.getMessage(anyString(), isNull(), eq(DEFAULT_LOCALE))).thenAnswer((Answer<String>) invocationOnMock -> {
			final String key = invocationOnMock.getArgument(0, String.class);
			return KEY_TEMPLATE_MAP.get(key);
		});
		when(messageSourceMock.getMessage(anyString(), any(Object[].class), eq(DEFAULT_LOCALE))).thenAnswer((Answer<String>) invocation -> {
			final String key = invocation.getArgument(0, String.class);
			final Object[] args = invocation.getArgument(1, Object[].class);
			return MessageFormat.format(KEY_TEMPLATE_MAP.get(key), args);
		});
	}

	@Test(expected = NullPointerException.class)
	public void testFailsToExecuteWhenNoAttributesSpecified() {
		PermissionChangedMessageSenderTask.builder().build().execute();
	}

	@Test
	public void testMessageSentWhenTaskProperlyInitializedAndNoRolesAndNoActivitiesRevoked() {
		PermissionChangedMessageSenderTask.builder()
				.gmailSender(gmailSenderMock)
				.messageSource(messageSourceMock)
				.locale(DEFAULT_LOCALE)
				.fromAddress(FROM_ADDRESS)
				.siteAddress(SITE_ADDRESS)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.login(LOGIN)
				.email(EMAIL)
				.alreadyGrantedRoles(of(UserRole.VIEWER))
				.actualRoles(of(UserRole.VIEWER, UserRole.OPERATOR))
				.alreadyConfirmedActivities(Collections.emptySet())
				.actualConfirmedActivities(of(UserActivityKind.CLINIC_ADVISOR))
				.build()
				.execute();

		final String bodyExpected = MessageFormat.format(GREETING_TEMPLATE, FIRST_NAME, LAST_NAME) + "\n"
				+ MessageFormat.format(PREFACE_TEMPLATE, LOGIN, SITE_ADDRESS) + "\n"
				+ "\n" // no revoked roles
				+ MessageFormat.format(ROLES_GRANTED_TEMPLATE, Lists.newArrayList(UserRole.OPERATOR.name())) + "\n"
				+ "\n" // no denied activities
				+ MessageFormat.format(APPROVED_ACTIVITIES_TEMPLATE, Lists.newArrayList(UserActivityKind.CLINIC_ADVISOR.name())) + "\n\n"
				+ MessageFormat.format(SUMMARY_TEMPLATE, Lists.newArrayList(UserRole.VIEWER.name(), UserRole.OPERATOR.name()), Lists.newArrayList(UserActivityKind.CLINIC_ADVISOR.name())) + "\n\n"
				+ BEST_REGARDS;

		verifyExpectedMessageWasSent(bodyExpected);
	}

	@Test
	public void testMessageSentWhenTaskProperlyInitializedAndNoSomeActivitiesAndRolesRevoked() {
		PermissionChangedMessageSenderTask.builder()
				.gmailSender(gmailSenderMock)
				.messageSource(messageSourceMock)
				.locale(DEFAULT_LOCALE)
				.fromAddress(FROM_ADDRESS)
				.siteAddress(SITE_ADDRESS)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.login(LOGIN)
				.email(EMAIL)
				.alreadyGrantedRoles(of(UserRole.VIEWER, UserRole.ADMINISTRATOR))
				.actualRoles(of(UserRole.VIEWER, UserRole.OPERATOR))
				.alreadyConfirmedActivities(of(UserActivityKind.EMPLOYER, UserActivityKind.CLINIC_ADVISOR))
				.actualConfirmedActivities(of(UserActivityKind.CLINIC_ADVISOR, UserActivityKind.PROPERTY_AGENT))
				.build()
				.execute();

		final String bodyExpected = MessageFormat.format(GREETING_TEMPLATE, FIRST_NAME, LAST_NAME) + "\n"
				+ MessageFormat.format(PREFACE_TEMPLATE, LOGIN, SITE_ADDRESS) + "\n"
				+ MessageFormat.format(ROLES_REVOKED_TEMPLATE, Lists.newArrayList(UserRole.ADMINISTRATOR.name())) + "\n" // no revoked roles
				+ MessageFormat.format(ROLES_GRANTED_TEMPLATE, Lists.newArrayList(UserRole.OPERATOR.name())) + "\n"
				+ MessageFormat.format(DENIED_ACTIVITIES_TEMPLATE, Lists.newArrayList(UserActivityKind.EMPLOYER.name())) + "\n" // no denied activities
				+ MessageFormat.format(APPROVED_ACTIVITIES_TEMPLATE, Lists.newArrayList(UserActivityKind.PROPERTY_AGENT)) + "\n\n"
				+ MessageFormat.format(SUMMARY_TEMPLATE, Lists.newArrayList(UserRole.VIEWER.name(), UserRole.OPERATOR.name()), Lists.newArrayList(UserActivityKind.PROPERTY_AGENT, UserActivityKind.CLINIC_ADVISOR.name())) + "\n\n"
				+ BEST_REGARDS;
		verifyExpectedMessageWasSent(bodyExpected);

	}

	void verifyExpectedMessageWasSent(String bodyExpected) {
		//noinspection ConstantConditions
		verify(gmailSenderMock).send(argThat((ArgumentMatcher<SimpleMailMessage>) argument -> argument != null
				&& isNotEmpty(argument.getTo())
				&& EMAIL.equals(argument.getTo()[0])
				&& MAIL_SUBJECT.equals(argument.getSubject())
				&& FROM_ADDRESS.equals(argument.getFrom())
				&& bodyExpected.equals(argument.getText())));
	}
}