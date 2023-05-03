package ua.southpost.resource.backup.web.security;

import com.google.common.collect.ImmutableSet;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableSet.copyOf;
import static com.google.common.collect.ImmutableSet.of;
import static ua.southpost.resource.backup.web.security.DomainRole.ERR_USER_KIND_DETERMINING_DOMAIN_REQUIRED;
import static ua.southpost.resource.backup.web.security.DomainRole.ERR_USER_ROLE_PARSE_FAILED;
import static org.junit.Assert.*;

public class DomainRoleTest {

	private static final String WRONG_KIND_OPERATOR = "WRONG_KIND_OPERATOR";
	private static final String WRONG = "WRONG";
	private static final String WRONG_ROLE_OF_CLINIC_ADVISOR = "CLINIC_ADVISOR_WRONG";
	@Rule
	public final ExpectedException expected = ExpectedException.none();

	@Test
	public void testDomainIndependent() {
		final Set<String> result = DomainRole.Builder.of(UserRole.VIEWER).buildSet(copyOf(UserActivityKind.values())).stream().map(DomainRole::getAuthorityName).collect(Collectors.toSet());
		final ImmutableSet<String> expected = of("ROLE_VIEWER");
		assertEquals(expected, result);
	}

	@Test
	public void testDomainDependent() {
		final Set<String> result = DomainRole.Builder.of(UserRole.OPERATOR).buildSet(of(UserActivityKind.EMPLOYER)).stream().map(DomainRole::getAuthorityName).collect(Collectors.toSet());
		final ImmutableSet<String> expected = of("ROLE_EMPLOYER_OPERATOR");
		assertEquals(expected, result);
	}

	@Test
	public void testDomainDependentMultiple() {
		final Set<String> result = DomainRole.Builder.of(UserRole.OPERATOR).buildSet(of(UserActivityKind.EMPLOYER, UserActivityKind.LAW_ASSISTANT)).stream().map(DomainRole::getAuthorityName).collect(Collectors.toSet());
		final ImmutableSet<String> expected = of("ROLE_EMPLOYER_OPERATOR", "ROLE_LAW_ASSISTANT_OPERATOR");
		assertEquals(expected, result);
	}

	@Test
	public void testParseKindSpecificRole() {
		final DomainRole expectedRole = DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, UserActivityKind.CLINIC_ADVISOR);
		final DomainRole domainRole = DomainRole.parseAuthorityName("ROLE_CLINIC_ADVISOR_OPERATOR");
		assertNotNull(domainRole);
		assertEquals(expectedRole, domainRole);
	}

	@Test
	public void testParseKindIndependentRole() {
		final DomainRole expectedRole = DomainRole.fromUserRole(UserRole.ADMINISTRATOR);
		final DomainRole domainRole = DomainRole.parseAuthorityName("ROLE_ADMINISTRATOR");
		assertNotNull(domainRole);
		assertEquals(expectedRole, domainRole);
	}

	@Test
	public void testWrongPrefixExceptionThrownWhenAuthorityNameHasNoRolePrefix() {
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage(DomainRole.ERR_NO_AUTHORITY_PREFIX + "CLINIC_ADVISOR_OPERATOR");
		DomainRole.parseAuthorityName("CLINIC_ADVISOR_OPERATOR");
	}

	@Test
	public void testUserRoleParseExceptionThrownWhenAuthorityNameContainsInvalidKindPart() {
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage(String.format(ERR_USER_ROLE_PARSE_FAILED, WRONG_KIND_OPERATOR));
		DomainRole.parseAuthorityName("ROLE_" + WRONG_KIND_OPERATOR);
	}

	@Test
	public void testUserRoleParseExceptionThrownWhenAuthorityNameContainsInvalidRolePart() {
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage(String.format(ERR_USER_ROLE_PARSE_FAILED, WRONG));
		DomainRole.parseAuthorityName("ROLE_" + WRONG_ROLE_OF_CLINIC_ADVISOR);
	}

	@Test
	public void testThatUserKindRequiredExceptionThrownWhenParsingRawOperatorAuthority() {
		expected.expect(IllegalArgumentException.class);
		expected.expectMessage(ERR_USER_KIND_DETERMINING_DOMAIN_REQUIRED);
		DomainRole.parseAuthorityName("ROLE_OPERATOR");
	}
}