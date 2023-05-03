package ua.southpost.resource.backup.api.endpoint;

import ua.southpost.resource.backup.api.endpoint.UserManagementEndpoint;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RunWith(MockitoJUnitRunner.class)
public class UserManagementEndpointTest {
	private static final long USER_ID = 99990L;
	private static final long INTERNAL_ID = 10001L;
	private static final long WRONG_ID = -10091L;

	@InjectMocks
	private UserManagementEndpoint testObj = new UserManagementEndpoint();
	@Mock
	private UserRepository userRepository;

	@Mock
	private User userMock;
	@Mock
	private User internalMock;

	@Before
	public void setUp() throws Exception {
		when(userRepository.findById(WRONG_ID)).thenReturn(Optional.empty());
		when(userRepository.findById(USER_ID)).thenReturn(Optional.of(userMock));
		when(userRepository.findById(INTERNAL_ID)).thenReturn(Optional.of(internalMock));
		when(internalMock.isInternalUser()).thenReturn(true);
		when(userMock.isInternalUser()).thenReturn(false);
	}

	@Test
	public void deleteWithWrongId() {
		//when
		testObj.delete(WRONG_ID);
		//then
		verify(userRepository).findById(WRONG_ID);
		verify(internalMock, never()).isInternalUser();
		verify(userMock, never()).isInternalUser();
		verify(userRepository, never()).delete(any(User.class));
	}

	@Test
	public void deleteInternalDoesNothing() {
		//when
		testObj.delete(INTERNAL_ID);
		//then
		verify(userRepository).findById(INTERNAL_ID);
		verify(internalMock).isInternalUser();
		verify(userMock, never()).isInternalUser();
		verify(userRepository, never()).delete(any(User.class));
	}

	@Test
	public void deleteRegularTakesPlace() {
		//when
		testObj.delete(USER_ID);
		//then
		verify(userRepository).findById(USER_ID);
		verify(internalMock, never()).isInternalUser();
		verify(userMock).isInternalUser();
		verify(userRepository).delete(userMock);
	}
}