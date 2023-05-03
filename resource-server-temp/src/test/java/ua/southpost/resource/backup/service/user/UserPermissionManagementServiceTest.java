package ua.southpost.resource.backup.service.user;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.service.mail.GmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPermissionManagementServiceTest {
	private static final String FROM_ADDRESS = "from@address.com";
	private static final String SITE_ADDRESS = "https://site.af=ddress.com";
	private static final long USER_ID = 1000L;
	private static final long WRONG_USER_ID = -1000L;
	private static final Set<UserRole> ALREADY_GRANTED_ROLES = EnumSet.of(UserRole.VIEWER, UserRole.OPERATOR);
	private static final Set<UserActivityKind> ALREADY_CONFIRMED_ACTIVITIES =  EnumSet.of(UserActivityKind.VISITOR);
	private static final Set<UserRole> NEWLY_GRANTED_ROLES = EnumSet.of(UserRole.VIEWER, UserRole.OPERATOR, UserRole.ADMINISTRATOR);
	private static final Set<UserActivityKind> NEWLY_CONFIRMED_ACTIVITIES = EnumSet.of(UserActivityKind.VISITOR, UserActivityKind.EMPLOYER);
	private static final Locale LOCALE = Locale.getDefault();


	@InjectMocks
	private UserPermissionManagementService testObj = new UserPermissionManagementServiceImpl();
	@Mock
	private UserRepository repositoryMock;
	@Mock
	private MessageSource messageSourceMock;
	@Mock
	private GmailSender gmailSender;
	private User userObj;

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(testObj, "fromAddress", FROM_ADDRESS);
		ReflectionTestUtils.setField(testObj, "siteAddress", SITE_ADDRESS);
		userObj = spy(new User());
		userObj.setId(USER_ID);
		userObj.setRoles(ALREADY_GRANTED_ROLES);
		userObj.setConfirmedActivities(ALREADY_CONFIRMED_ACTIVITIES);
		when(userObj.getRoles()).thenReturn(ALREADY_GRANTED_ROLES);
		when(userObj.getConfirmedActivities()).thenReturn(ALREADY_CONFIRMED_ACTIVITIES);
		when(repositoryMock.findById(USER_ID)).thenReturn(Optional.of(userObj));
		when(repositoryMock.findById(WRONG_USER_ID)).thenReturn(Optional.empty());
		when(repositoryMock.save(userObj)).thenReturn(userObj);
	}

	@Test(expected = NotFoundException.class)
	public void testWrongUserIdScenario() {
		try {
			testObj.updateUserPermissions(WRONG_USER_ID, NEWLY_GRANTED_ROLES, NEWLY_CONFIRMED_ACTIVITIES, LOCALE);
		} finally {
			verify(repositoryMock, never()).save(any(User.class));
		}
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Test
	public void testValidUserIdScenario() {
		//when
		testObj.updateUserPermissions(USER_ID, NEWLY_GRANTED_ROLES, NEWLY_CONFIRMED_ACTIVITIES, LOCALE);
		//then
		verify(userObj).getRoles();
		verify(userObj).getConfirmedActivities();
		verify(userObj, never()).getDeclaredActivities();
		verify(userObj).setRoles(NEWLY_GRANTED_ROLES);
		verify(userObj).setConfirmedActivities(NEWLY_CONFIRMED_ACTIVITIES);
		verify(userObj, never()).setDeclaredActivities(anySet());
		verify(repositoryMock).save(userObj);
		verify(gmailSender).send(any(SimpleMailMessage.class));
	}
}