package ua.southpost.resource.backup.api.mapper;

import ua.southpost.resource.backup.data.model.User;
import ua.southpost.resource.commons.model.dto.BriefUserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BriefUserInfoMapperTest {
	private static final Locale NATIVE_LOCALE = new Locale("uk", "UA");
	private static final long USER_ID = 9999L;
	private static final String LOGIN = "login";
	private static final String EMAIL = "user@mail.com";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

	private BriefUserInfoMapper testObj = new BriefUserInfoMapper();

	@Mock
	private User userMock;

	@Before
	public void setUp() {
		when(userMock.getId()).thenReturn(USER_ID);
		when(userMock.getLogin()).thenReturn(LOGIN);
		when(userMock.getEmail()).thenReturn(EMAIL);
		when(userMock.getFirstName()).thenReturn(FIRST_NAME);
		when(userMock.getLastName()).thenReturn(LAST_NAME);
	}

	@Test
	public void testMap() {
		final BriefUserInfo userInfo = testObj.map(userMock, NATIVE_LOCALE);

		assertNotNull(userInfo);
		assertEquals((Long)USER_ID, userInfo.getId());
		assertEquals(LOGIN, userInfo.getLogin());
		assertEquals(EMAIL, userInfo.getEmail());
		assertEquals(FIRST_NAME, userInfo.getFirstName());
		assertEquals(LAST_NAME, userInfo.getLastName());
	}
}