package ua.southpost.resource.backup.service.user;

import com.google.common.collect.Lists;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.mail.GmailSender;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ua.southpost.resource.backup.service.user.UserPermissionRequestNotificationServiceImpl.DEFAULT_MESSAGE_TEMPLATE;
import static ua.southpost.resource.backup.service.user.UserPermissionRequestNotificationServiceImpl.DEFAULT_SUBJECT_TEMPLATE;
import static ua.southpost.resource.backup.service.user.UserPermissionRequestNotificationServiceImpl.MAX_RECIPIENTS_NUMBER;
import static ua.southpost.resource.backup.service.user.UserPermissionRequestNotificationServiceImpl.MESSAGE_KEY;
import static ua.southpost.resource.backup.service.user.UserPermissionRequestNotificationServiceImpl.SUBJECT_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPermissionRequestNotificationServiceTest {
	private static final Locale LOCALE = Locale.getDefault();
	private static final String FROM_ADDRESS = "from@address.com";
	private static final long USER_ID = 1000L;
	private static final String LOGIN = "login";
	private static final String EMAIL = "user@email.com";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	private static final String SUPERVISOR_1_EMAIL = "supervisor1@email.com";
	private static final String SUPERVISOR_2_EMAIL = "supervisor2@email.com";
	private static final String LOCALIZED_SUBJECT = "localized subject";
	private static final String LOCALIZED_E_MAIL_MESSAGE_BODY = "Localized e-mail message body";
	private Set<UserActivityKind> TRIVIAL_ACTIVITIES = EnumSet.of(UserActivityKind.VISITOR);
	private Set<UserActivityKind> NOTIFICATION_REQUIRED_ACTIVITIES = EnumSet.of(UserActivityKind.EMPLOYER, UserActivityKind.PROPERTY_AGENT);



	@InjectMocks
	private UserPermissionRequestNotificationService testObj = new UserPermissionRequestNotificationServiceImpl();
	@Mock
	private UserRepository userRepository;
	@Mock
	private MessageSource messageSource;
	@Mock
	private GmailSender gmailSender;
	@Mock
	private User supervisor1Mock;
	@Mock
	private User supervisor2Mock;

	private DetailedUserInfo userInfo;

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtils.setField(testObj, "fromAddress", FROM_ADDRESS);
		userInfo = new DetailedUserInfo();
		userInfo.setId(USER_ID);
		userInfo.setLogin(LOGIN);
		userInfo.setEmail(EMAIL);
		userInfo.setFirstName(FIRST_NAME);
		userInfo.setLastName(LAST_NAME);
		userInfo.setDeclaredActivities(mapActivitySet(TRIVIAL_ACTIVITIES));
		when(supervisor1Mock.getEmail()).thenReturn(SUPERVISOR_1_EMAIL);
		when(supervisor2Mock.getEmail()).thenReturn(SUPERVISOR_2_EMAIL);

	}

	private ArgumentMatcher<Pageable> isPagebleMatches() {
		return argument -> argument.isPaged()
				&& argument.getPageSize() == MAX_RECIPIENTS_NUMBER
				&& argument.getOffset() == 0L
				&& Objects.equals(argument.getSort(), UserRepository.DEFAULT_SORT);
	}

	private Set<AbstractEntityInfo<UserActivityKind>> mapActivitySet(Set<UserActivityKind> activitySet) {
		return activitySet.stream().map(this::mapActivity).collect(Collectors.toSet());
	}

	AbstractEntityInfo<UserActivityKind> mapActivity(UserActivityKind activity) {
		final AbstractEntityInfo<UserActivityKind> activityInfo = new AbstractEntityInfo<>();
		activityInfo.setId(activity);
		activityInfo.setTextValue(activity.name());
		return activityInfo;
	}

	@Test
	public void testNoPermissionRequestRequiredScenario() {
		//when
		testObj.sendPermissionRequiredNotification(userInfo, LOCALE);
		//then
		verify(userRepository, never()).countWithRole(UserRole.SUPERVISOR);
		verify(userRepository, never()).listWithRole(eq(UserRole.SUPERVISOR), argThat(isPagebleMatches()));
		verify(gmailSender, never()).send(any(SimpleMailMessage.class));
	}

	@Test
	public void testNotificationRequiredButNoSupervisorsFoundScenario() {
		//given
		userInfo.setDeclaredActivities(mapActivitySet(NOTIFICATION_REQUIRED_ACTIVITIES));
		when(userRepository.countWithRole(UserRole.SUPERVISOR)).thenReturn(0L);
		//when
		testObj.sendPermissionRequiredNotification(userInfo, LOCALE);
		//then
		verify(userRepository).countWithRole(UserRole.SUPERVISOR);
		verify(userRepository, never()).listWithRole(eq(UserRole.SUPERVISOR), argThat(isPagebleMatches()));
		verify(gmailSender, never()).send(any(SimpleMailMessage.class));
	}

	@Test
	public void testNotificationRequiredAndSomeSupervisorsFound() {
		//given
		final Object[] subjectParams = {LOGIN};
		final String defaultSubject = MessageFormat.format(DEFAULT_SUBJECT_TEMPLATE, subjectParams);
		final Object[] bodyParams = {LOGIN, FIRST_NAME, LAST_NAME, EMAIL};
		final String defaultBody = MessageFormat.format(DEFAULT_MESSAGE_TEMPLATE, bodyParams);
		userInfo.setDeclaredActivities(mapActivitySet(NOTIFICATION_REQUIRED_ACTIVITIES));
		when(userRepository.countWithRole(UserRole.SUPERVISOR)).thenReturn(2L);
		when(userRepository.listWithRole(eq(UserRole.SUPERVISOR), argThat(isPagebleMatches())))
				.thenReturn(Lists.newArrayList(supervisor1Mock, supervisor2Mock));
		//when
		testObj.sendPermissionRequiredNotification(userInfo, LOCALE);
		//then
		verify(userRepository).countWithRole(UserRole.SUPERVISOR);
		verify(userRepository).listWithRole(eq(UserRole.SUPERVISOR), argThat(isPagebleMatches()));
		verify(gmailSender).send(argThat(mailMessageMatcher(defaultSubject, defaultBody)));
	}

	@Test
	public void testNotificationRequiredAndSomeSupervisorsFoundAndLocalizedMessagesPresent() {
		//given
		final Object[] subjectParams = {LOGIN};
		final String defaultSubject = MessageFormat.format(DEFAULT_SUBJECT_TEMPLATE, subjectParams);
		when(messageSource.getMessage(SUBJECT_KEY, subjectParams, defaultSubject, LOCALE))
				.thenReturn(LOCALIZED_SUBJECT);
		final Object[] bodyParams = {LOGIN, FIRST_NAME, LAST_NAME, EMAIL};
		final String defaultBody = MessageFormat.format(DEFAULT_MESSAGE_TEMPLATE, bodyParams);
		when(messageSource.getMessage(MESSAGE_KEY, bodyParams, defaultBody, LOCALE))
				.thenReturn(LOCALIZED_E_MAIL_MESSAGE_BODY);
		userInfo.setDeclaredActivities(mapActivitySet(NOTIFICATION_REQUIRED_ACTIVITIES));
		when(userRepository.countWithRole(UserRole.SUPERVISOR)).thenReturn(2L);
		when(userRepository.listWithRole(eq(UserRole.SUPERVISOR), argThat(isPagebleMatches())))
				.thenReturn(Lists.newArrayList(supervisor1Mock, supervisor2Mock));
		//when
		testObj.sendPermissionRequiredNotification(userInfo, LOCALE);
		//then
		verify(userRepository).countWithRole(UserRole.SUPERVISOR);
		verify(userRepository).listWithRole(eq(UserRole.SUPERVISOR), argThat(isPagebleMatches()));
		verify(gmailSender).send(argThat(mailMessageMatcher(LOCALIZED_SUBJECT, LOCALIZED_E_MAIL_MESSAGE_BODY)));
	}


	private ArgumentMatcher<SimpleMailMessage> mailMessageMatcher(String subject, String body) {
		return argument -> subject.equals(argument.getSubject()) && body.equals(argument.getText());
	}

}