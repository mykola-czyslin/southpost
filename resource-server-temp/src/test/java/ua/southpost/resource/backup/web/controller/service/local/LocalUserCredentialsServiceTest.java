package ua.southpost.resource.backup.web.controller.service.local;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.backup.service.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RunWith(MockitoJUnitRunner.class)
public class LocalUserCredentialsServiceTest {
	private static final long USER_ID = 1001L;
	private static final long WRONG_USER_ID = -1001L;
	private static final String PLAIN_OLD = "plain old password";
	private static final String ENCODED_OLD = "encoded old password";
	private static final String PLAIN_NEW = "plain new password";
	private static final String ENCODED_NEW = "encoded new password";
	private static final String PLAIN_WRONG = "plain wrong password";
	private static final String ENCODED_WRONG = "encoded wrong password";

	@InjectMocks
	private LocalUserCredentialsService testObj = new LocalUserCredentialsService();
	@Mock
	private UserRepository userRepositoryMock;
	@Mock
	private PasswordEncoder passwordEncoderMock;
	@Mock
	private User userMock;

	@Before
	public void setUp() {
		when(passwordEncoderMock.encode(PLAIN_OLD)).thenReturn(ENCODED_OLD);
		when(passwordEncoderMock.encode(PLAIN_NEW)).thenReturn(ENCODED_NEW);
		when(passwordEncoderMock.encode(PLAIN_WRONG)).thenReturn(ENCODED_WRONG);
		when(userMock.getPassword()).thenReturn(ENCODED_OLD);
		when(userRepositoryMock.save(userMock)).thenReturn(userMock);
		when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(userMock));
		when(userRepositoryMock.findById(WRONG_USER_ID)).thenReturn(Optional.empty());
	}

	@Test
	public void testHappyPathScenario() {
		//given

		testObj.updateCredentials(USER_ID, PLAIN_OLD, PLAIN_NEW);

		verify(userMock).setPassword(ENCODED_NEW);
		verify(userRepositoryMock).findById(USER_ID);
		verify(userRepositoryMock).save(userMock);
	}

	@Test(expected = NotFoundException.class)
	public void testWrongUserIdScenario() {
		try {
			testObj.updateCredentials(WRONG_USER_ID, PLAIN_OLD, PLAIN_NEW);
		} finally {
			verify(userMock, never()).getPassword();
			verify(userMock, never()).setPassword(anyString());
			verify(userRepositoryMock, never()).save(any(User.class));
		}
	}

	@Test(expected = NotFoundException.class)
	public void testWrongOldPasswordScenario() {
		try {
			testObj.updateCredentials(USER_ID, PLAIN_WRONG, PLAIN_NEW);
		} finally {
			verify(userMock).getPassword();
			verify(userMock, never()).setPassword(anyString());
			verify(userRepositoryMock, never()).save(any(User.class));
		}
	}
}