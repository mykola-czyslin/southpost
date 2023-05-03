package ua.southpost.resource.backup.web.service.entity;

import ua.southpost.resource.commons.service.LocalizedEntityMapper;
import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocalUserDataServiceTest {
	private static final long USER_ID = 10001L;
	private static final long WRONG_USER_ID = -10001L;
	private static final String ENCODED_PASSWORD = "encoded password";
	private static final String LOGIN = "login";
	private static final String WRONG_LOGIN = "wrong login";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Lennon";
	private static final String EMAIL = "john.lennon@the.beatles.co.uk";
	private static final Set<UserRole> USER_ROLES = EnumSet.of(UserRole.VIEWER, UserRole.OPERATOR);
	private static final Set<UserActivityKind> DECLARED_USER_ACTIVITY_KINDS = EnumSet.of(UserActivityKind.EMPLOYER, UserActivityKind.PROPERTY_AGENT);
	private static final Set<UserActivityKind> CONFIRMED_USER_ACTIVITY_KINDS = EnumSet.of(UserActivityKind.EMPLOYER);
	private static final Locale LOCALE = Locale.getDefault();
	private static final int YEAR = 2020;
	private static final int MONTH = Calendar.MARCH;
	private static final int DAY = 11;
	private static final int HOUR = 9;
	private static final int MINUTE = 33;
	private static final Date REGISTERED_AT;

	static  {
		Calendar calendar = GregorianCalendar.getInstance(LOCALE);
		calendar.set(Calendar.YEAR, YEAR);
		calendar.set(Calendar.MONTH, MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, DAY);
		calendar.set(Calendar.HOUR_OF_DAY, HOUR);
		calendar.set(Calendar.MINUTE, MINUTE);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		REGISTERED_AT = calendar.getTime();
	}


	@InjectMocks
	private LocalUserDataService testObj = new LocalUserDataService();

	@Mock
	private UserRepository repositoryMock;
	@Mock
	private LocalizedEntityMapper<User, DetailedUserInfo, Long> entityInfoMapperMock;
	@Mock
	private User userMock;



	@Before
	public void setUp() {
		when(repositoryMock.findById(USER_ID)).thenReturn(Optional.of(userMock));
		when(repositoryMock.findById(WRONG_USER_ID)).thenReturn(Optional.empty());
		when(repositoryMock.findByLogin(LOGIN)).thenReturn(Optional.of(userMock));
		when(repositoryMock.findByLogin(WRONG_LOGIN)).thenReturn(Optional.empty());
		when(userMock.getId()).thenReturn(USER_ID);
		when(userMock.getLogin()).thenReturn(LOGIN);
		when(userMock.getEmail()).thenReturn(EMAIL);
		when(userMock.getPassword()).thenReturn(ENCODED_PASSWORD);
		when(userMock.getFirstName()).thenReturn(FIRST_NAME);
		when(userMock.getLastName()).thenReturn(LAST_NAME);
		when(userMock.getRoles()).thenReturn(USER_ROLES);
		when(userMock.getDeclaredActivities()).thenReturn(DECLARED_USER_ACTIVITY_KINDS);
		when(userMock.getConfirmedActivities()).thenReturn(CONFIRMED_USER_ACTIVITY_KINDS);
		when(userMock.getRegisteredAt()).thenReturn(REGISTERED_AT);

		when(entityInfoMapperMock.map(any(User.class), any(Locale.class))).then((Answer<DetailedUserInfo>)this::mapUser);
	}

	@Test
	public void testFindByIdWithWrongId() {
		Optional<DetailedUserInfo> result = testObj.byId(WRONG_USER_ID, LOCALE);

		assertFalse(result.isPresent());
		verify(entityInfoMapperMock, never()).map(any(User.class), any(Locale.class));
	}

	@Test
	public void testFindByLoginWithWrongLogin() {
		Optional<DetailedUserInfo> result = testObj.byLogin(WRONG_LOGIN, LOCALE);

		assertFalse(result.isPresent());
		verify(entityInfoMapperMock, never()).map(any(User.class), any(Locale.class));
	}

	@Test
	public void testFindByIdWithValidId() {
		//given
		final Set<AbstractEntityInfo<UserRole>> expectedRoles
				= USER_ROLES.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		final Set<AbstractEntityInfo<UserActivityKind>> expectedDeclaredActivitySet
				= DECLARED_USER_ACTIVITY_KINDS.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		final Set<AbstractEntityInfo<UserActivityKind>> expectedConfirmedActivitySet
				= CONFIRMED_USER_ACTIVITY_KINDS.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());


		// when
		Optional<DetailedUserInfo> result = testObj.byId(USER_ID, LOCALE);

		//then
		assertTrue(result.isPresent());
		verify(entityInfoMapperMock).map(userMock, LOCALE);
		DetailedUserInfo userInfo = result.get();
		assertEquals((Long) USER_ID, userInfo.getId());
		assertEquals(LOGIN, userInfo.getLogin());
		assertEquals(EMAIL, userInfo.getEmail());
		assertEquals(FIRST_NAME, userInfo.getFirstName());
		assertEquals(LAST_NAME, userInfo.getLastName());
		assertEquals(ENCODED_PASSWORD, userInfo.getPassword());
		assertEquals(expectedRoles, userInfo.getRoles());
		assertEquals(expectedDeclaredActivitySet, userInfo.getDeclaredActivities());
		assertEquals(expectedConfirmedActivitySet, userInfo.getConfirmedActivities());
		assertEquals(REGISTERED_AT, userInfo.getRegisteredAt());
	}

	@Test
	public void testFindByLoginWithValidLogin() {
		//given
		final Set<AbstractEntityInfo<UserRole>> expectedRoles
				= USER_ROLES.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		final Set<AbstractEntityInfo<UserActivityKind>> expectedDeclaredActivitySet
				= DECLARED_USER_ACTIVITY_KINDS.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		final Set<AbstractEntityInfo<UserActivityKind>> expectedConfirmedActivitySet
				= CONFIRMED_USER_ACTIVITY_KINDS.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		//when
		Optional<DetailedUserInfo> result = testObj.byLogin(LOGIN, LOCALE);

		//then
		assertTrue(result.isPresent());
		verify(entityInfoMapperMock).map(userMock, LOCALE);
		DetailedUserInfo userInfo = result.get();
		assertEquals((Long) USER_ID, userInfo.getId());
		assertEquals(LOGIN, userInfo.getLogin());
		assertEquals(EMAIL, userInfo.getEmail());
		assertEquals(FIRST_NAME, userInfo.getFirstName());
		assertEquals(LAST_NAME, userInfo.getLastName());
		assertEquals(ENCODED_PASSWORD, userInfo.getPassword());
		assertEquals(expectedRoles, userInfo.getRoles());
		assertEquals(expectedDeclaredActivitySet, userInfo.getDeclaredActivities());
		assertEquals(expectedConfirmedActivitySet, userInfo.getConfirmedActivities());
		assertEquals(REGISTERED_AT, userInfo.getRegisteredAt());
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
		Set<AbstractEntityInfo<UserRole>> userRoles
				= ofNullable(user.getRoles())
				.orElseGet(Collections::emptySet)
				.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		userInfo.setRoles(userRoles);
		Set<AbstractEntityInfo<UserActivityKind>> declaredActivitySet
				= ofNullable(user.getDeclaredActivities())
				.orElseGet(Collections::emptySet)
				.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		userInfo.setDeclaredActivities(declaredActivitySet);
		Set<AbstractEntityInfo<UserActivityKind>> confirmedUserActivitySet
				= ofNullable(user.getConfirmedActivities())
				.orElseGet(Collections::emptySet)
				.stream()
				.map(this::wrapEnum)
				.collect(Collectors.toSet());
		userInfo.setConfirmedActivities(confirmedUserActivitySet);
		userInfo.setRegisteredAt(user.getRegisteredAt());
		return userInfo;
	}

	private <T extends Enum<?>> AbstractEntityInfo<T> wrapEnum(T enumItem) {
		final AbstractEntityInfo<T> entityInfo = new AbstractEntityInfo<>();
		entityInfo.setId(enumItem);
		entityInfo.setTextValue(enumItem.name());
		return entityInfo;
	}

}