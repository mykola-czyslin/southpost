package ua.southpost.resource.backup.web.security;

import com.google.common.collect.ImmutableSet;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityUtilsTest {

	private static final String URI_CLINIC_LIST = "/manage/clinic/list";
	private static final String ROLE_ADMINISTRATOR_AUTHORITY = SecurityUtils.ROLE_PREFIX + UserRole.ADMINISTRATOR;
	private static final String ROLE_SUPERVISOR_AUTHORITY = SecurityUtils.ROLE_PREFIX + UserRole.SUPERVISOR;
	private static final String ROLE_CLINIC_OPERATOR_AUTHORITY = SecurityUtils.ROLE_PREFIX +
			UserActivityKind.CLINIC_ADVISOR + SecurityUtils.DELIMITER + UserRole.OPERATOR;
	private static final String ROLE_EMPLOYER_OPERATOR_AUTHORITY = SecurityUtils.ROLE_PREFIX +
			UserActivityKind.EMPLOYER + SecurityUtils.DELIMITER + UserRole.OPERATOR;

	@Mock
	private UserDetails userDetailsMock;
	private final GrantedAuthority supervisorAuthority = new SimpleGrantedAuthority(ROLE_SUPERVISOR_AUTHORITY);
	private final GrantedAuthority clinicOperatorAuthorityMock = new SimpleGrantedAuthority(ROLE_CLINIC_OPERATOR_AUTHORITY);
	private final GrantedAuthority employerOperatorAuthorityMock = new SimpleGrantedAuthority(ROLE_EMPLOYER_OPERATOR_AUTHORITY);
	@Mock
	private HttpServletRequest requestMock;
	@Mock
	private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationTokenMock;
	@Mock
	private AnonymousAuthenticationToken anonymousAuthenticationTokenMock;

	@Before
	public void setUp() {
		when(requestMock.getUserPrincipal()).thenReturn(usernamePasswordAuthenticationTokenMock);
		when(usernamePasswordAuthenticationTokenMock.getPrincipal()).thenReturn(userDetailsMock);
		doReturn(Collections.singletonList(supervisorAuthority)).when(userDetailsMock).getAuthorities();
	}

	@Test
	public void testResolveUserPrincipalResolvesToUserDetailsForUserNamePasswordAuthenticationToken() {

		final Optional<UserDetails> userDetailOpt = SecurityUtils.resolvePrincipal(requestMock);

		assertTrue(userDetailOpt.isPresent());
		userDetailOpt.ifPresent(ud -> assertEquals(userDetailsMock, ud));
	}

	@Test
	public void testResolveUserPrincipalResolvesToEmptyForAnonymousAuthenticationToken() {
		when(requestMock.getUserPrincipal()).thenReturn(anonymousAuthenticationTokenMock);

		final Optional<UserDetails> userDetailOpt = SecurityUtils.resolvePrincipal(requestMock);

		assertFalse(userDetailOpt.isPresent());
	}

	@Test
	public void testSupervisorIsGrantedAdminPermissions() {

		assertTrue(SecurityUtils.isAdmin(requestMock));
	}

	@Test
	public void testSupervisorIsGrantedStaffPermissionsForClinicUri() {
		when(requestMock.getRequestURI()).thenReturn(URI_CLINIC_LIST);

		assertTrue(SecurityUtils.isStaff(requestMock));
	}

	@Test
	public void testClinicOperatorIsNotGrantedAdminPermissions() {
		doReturn(Collections.singletonList(clinicOperatorAuthorityMock)).when(userDetailsMock).getAuthorities();

		assertFalse(SecurityUtils.isAdmin(requestMock));
	}

	@Test
	public void testClinicOperatorIsGrantedStaffPermissionsForClinicUri() {
		doReturn(Collections.singletonList(clinicOperatorAuthorityMock)).when(userDetailsMock).getAuthorities();
		when(requestMock.getRequestURI()).thenReturn(URI_CLINIC_LIST);

		assertTrue(SecurityUtils.isStaff(requestMock));
	}

	@Test
	public void testEmployerOperatorIsNotGrantedStaffPermissionsForClinicUri() {
		doReturn(Collections.singletonList(employerOperatorAuthorityMock)).when(userDetailsMock).getAuthorities();

		when(requestMock.getRequestURI()).thenReturn(URI_CLINIC_LIST);

		assertFalse(SecurityUtils.isStaff(requestMock));
	}


	@Test
	public void testMapRolesToAuthority() {
		final List<SimpleGrantedAuthority> roleSet = SecurityUtils.mapRolesToAuthorities(ImmutableSet.of(DomainRole.parseAuthorityName(ROLE_ADMINISTRATOR_AUTHORITY)));

		assertNotNull(roleSet);
		assertEquals(1, roleSet.size());
		final Optional<SimpleGrantedAuthority> authorityOptional = roleSet.stream().findFirst();
		assertTrue(authorityOptional.isPresent());
		authorityOptional.ifPresent(a -> assertEquals(ROLE_ADMINISTRATOR_AUTHORITY, a.getAuthority()));
	}
}