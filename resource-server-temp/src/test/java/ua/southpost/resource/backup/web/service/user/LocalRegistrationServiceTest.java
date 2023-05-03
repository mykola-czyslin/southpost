package ua.southpost.resource.backup.web.service.user;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalRegistrationServiceTest {
	private static final long USER_ID = 10001L;
	private static final String PLAIN_PASSWORD = "plain password";
	private static final String ENCODED_PASSWORD = "encoded password";
	private static final String LOGIN = "login";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Lennon";
	private static final String EMAIL = "john.lennon@the.beatles.co.uk";
	private static final Set<UserActivityKind> USER_ACTIVITY_KINDS = EnumSet.of(UserActivityKind.EMPLOYER, UserActivityKind.PROPERTY_AGENT);
	static final Locale LOCALE = Locale.getDefault();

	@InjectMocks
	private LocalRegistrationService testObj = new LocalRegistrationService();
	@Mock
	private PasswordEncoder encoderMock;
	@Mock
	private UserRepository repositoryMock;
	@Mock
	private LocalizedEntityMapper<User, DetailedUserInfo, Long> userInfoMapperMock;

	private UserRegistrationRequest userRegistrationRequest;

	@Before
	public void setUp() {
		when(encoderMock.encode(PLAIN_PASSWORD)).thenReturn(ENCODED_PASSWORD);
		when(repositoryMock.save(any(User.class))).then((Answer<User>) this::saveUser);
		userRegistrationRequest = UserRegistrationRequest
				.builder()
				.login(LOGIN)
				.email(EMAIL)
				.firstName(FIRST_NAME)
				.lastName(LAST_NAME)
				.password(PLAIN_PASSWORD)
				.declaredActivities(USER_ACTIVITY_KINDS)
				.build();
		when(userInfoMapperMock.map(any(User.class), any(Locale.class))).then((Answer<DetailedUserInfo>) this::mapUser);
	}

	@Test
	public void testHappyPathScenario() throws Exception {
		final Date startDate = new Date();
		final Set<AbstractEntityInfo<UserActivityKind>> expectedActivitySet
				= USER_ACTIVITY_KINDS.stream()
				.map(this::wrapActivity)
				.collect(Collectors.toSet());
		TimeUnit.MILLISECONDS.sleep(50L);


		final DetailedUserInfo registered = testObj.register(userRegistrationRequest, LOCALE);

		assertNotNull(registered);
		Assert.assertEquals((Long) USER_ID, registered.getId());
		assertEquals(LOGIN, registered.getLogin());
		assertEquals(EMAIL, registered.getEmail());
		assertEquals(FIRST_NAME, registered.getFirstName());
		assertEquals(LAST_NAME, registered.getLastName());
		assertEquals(ENCODED_PASSWORD, registered.getPassword());
		assertEquals(expectedActivitySet, registered.getDeclaredActivities());
		assertTrue(isEmpty(registered.getConfirmedActivities()));
		assertTrue(startDate.before(registered.getRegisteredAt()));
	}

	private User saveUser(InvocationOnMock invocation) {
		final User user = invocation.getArgument(0, User.class);
		user.setId(USER_ID);
		return user;
	}

	private DetailedUserInfo mapUser(InvocationOnMock invocation) {
		final User user = invocation.getArgument(0, User.class);
		DetailedUserInfo userInfo = new DetailedUserInfo();
		userInfo.setId(user.getId());
		userInfo.setLogin(user.getLogin());
		userInfo.setEmail(user.getEmail());
		userInfo.setFirstName(user.getFirstName());
		userInfo.setLastName(user.getLastName());
		userInfo.setPassword(user.getPassword());
		Set<AbstractEntityInfo<UserActivityKind>> activitySet
				= ofNullable(user.getDeclaredActivities())
				.orElseGet(Collections::emptySet)
				.stream()
				.map(this::wrapActivity)
				.collect(Collectors.toSet());
		userInfo.setDeclaredActivities(activitySet);
		userInfo.setRegisteredAt(user.getRegisteredAt());
		return userInfo;
	}

	private AbstractEntityInfo<UserActivityKind> wrapActivity(UserActivityKind activityKind) {
		final AbstractEntityInfo<UserActivityKind> entityInfo = new AbstractEntityInfo<>();
		entityInfo.setId(activityKind);
		entityInfo.setTextValue(activityKind.name());
		return entityInfo;
	}
}