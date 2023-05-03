package ua.southpost.resource.backup.web.controller.service.local;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import ua.southpost.resource.backup.web.controller.service.UserProfilePayload;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalUserProfileServiceTest {
	private static final long USER_ID = 10001L;
	private static final long WRONG_USER_ID = -10001L;
	private static final String LOGIN = "login";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Lennon";
	private static final String EMAIL = "john.lennon@the.beatles.co.uk";
	private static final Set<UserActivityKind> USER_ACTIVITY_KINDS = EnumSet.of(UserActivityKind.EMPLOYER, UserActivityKind.PROPERTY_AGENT);

	@InjectMocks
	private LocalUserProfileService testObj = new LocalUserProfileService();
	@Mock
	private UserRepository repositoryMock;
	@Mock
	private User userMock;
	@Mock
	private UserProfilePayload emptyPayloadMock;
	@Mock
	private UserProfilePayload wrongUserIdPayloadMock;
	@Mock
	private UserProfilePayload payloadMock;

	@Before
	public void setUp() {
		when(repositoryMock.findById(USER_ID)).thenReturn(Optional.of(userMock));
		when(repositoryMock.findById(WRONG_USER_ID)).thenReturn(Optional.empty());
		when(repositoryMock.save(userMock)).thenReturn(userMock);
		when(emptyPayloadMock.getUserId()).thenReturn(USER_ID);
		when(emptyPayloadMock.getLogin()).thenReturn(null);
		when(emptyPayloadMock.getEmail()).thenReturn(null);
		when(emptyPayloadMock.getFirstName()).thenReturn(null);
		when(emptyPayloadMock.getLastName()).thenReturn(null);
		when(emptyPayloadMock.getUserActivities()).thenReturn(null);
		when(wrongUserIdPayloadMock.getUserId()).thenReturn(WRONG_USER_ID);
		when(payloadMock.getUserId()).thenReturn(USER_ID);
		when(payloadMock.getLogin()).thenReturn(LOGIN);
		when(payloadMock.getEmail()).thenReturn(EMAIL);
		when(payloadMock.getFirstName()).thenReturn(FIRST_NAME);
		when(payloadMock.getLastName()).thenReturn(LAST_NAME);
		when(payloadMock.getUserActivities()).thenReturn(USER_ACTIVITY_KINDS);
	}

	@Test(expected = NotFoundException.class)
	public void testWrongUserIdScenario() {
		try {
			testObj.updateProfile(wrongUserIdPayloadMock);
		} finally {
			verify(userMock, never()).setLogin(anyString());
			verify(userMock, never()).setEmail(anyString());
			verify(userMock, never()).setFirstName(anyString());
			verify(userMock, never()).setLastName(anyString());
			verify(userMock, never()).setDeclaredActivities(anySet());
			verify(repositoryMock, never()).save(any(User.class));
		}
	}

	@Test
	public void testEmptyPayloadScenario() {
		testObj.updateProfile(emptyPayloadMock);

		verify(userMock, never()).setLogin(anyString());
		verify(userMock, never()).setEmail(anyString());
		verify(userMock, never()).setFirstName(anyString());
		verify(userMock, never()).setLastName(anyString());
		verify(userMock, never()).setDeclaredActivities(anySet());

		verify(repositoryMock).save(userMock);
	}

	@Test
	public void testHappyPathFullUpdateScenario() {
		testObj.updateProfile(payloadMock);


		verify(userMock).setLogin(LOGIN);
		verify(userMock).setEmail(EMAIL);
		verify(userMock).setFirstName(FIRST_NAME);
		verify(userMock).setLastName(LAST_NAME);
		verify(userMock).setDeclaredActivities(USER_ACTIVITY_KINDS);

		verify(repositoryMock).save(userMock);
	}
}