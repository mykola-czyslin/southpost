package ua.southpost.resource.backup.web.security;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableSet.of;
import static ua.southpost.resource.backup.web.security.DomainRole.fromUserRole;

public class SecurityUtils {

	public static final Set<DomainRole> ADMINISTRATIVE_ROLES =
			ImmutableSet.of(fromUserRole(UserRole.ADMINISTRATOR), fromUserRole(UserRole.SUPERVISOR));
	static final String ROLE_PREFIX = "ROLE_";
	static final String DELIMITER = "_";
	static final String ROLE_NAME_VIEWER = ROLE_PREFIX + UserRole.VIEWER.name();
	private static final Map<UserActivityKind, Set<DomainRole>> USER_KIND_STAFF_ROLE_SET_MAP = Arrays.stream(UserActivityKind.values())
			.collect(Collectors.toMap(k -> k, k -> k.isRoleDeterminant() ? of(DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, k)) : of()));
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

	private SecurityUtils() {
	}

	@Nonnull
	public static Optional<UserDetails> resolvePrincipal(@Nonnull HttpServletRequest request) {
		return Optional.ofNullable(request.getUserPrincipal())
				.filter(Authentication.class::isInstance)
				.filter(p -> !(p instanceof AnonymousAuthenticationToken))
				.map(Authentication.class::cast)
				.map(Authentication::getPrincipal)
				.filter(u -> u instanceof UserDetails)
				.map(UserDetails.class::cast);
	}

	public static boolean isStaff(@Nonnull HttpServletRequest request) {
		final Set<DomainRole> staffRoles = Sets.newHashSet(ADMINISTRATIVE_ROLES);
		staffRoles.addAll(minimalStaffRoles(request));
		return anyRoleGranted(request, staffRoles);
	}

	public static boolean isAdmin(@Nonnull HttpServletRequest request) {
		return anyRoleGranted(request, ADMINISTRATIVE_ROLES);
	}

	private static boolean anyRoleGranted(@Nonnull HttpServletRequest request, @Nonnull Set<DomainRole> permissionRoles) {
		return resolvePrincipal(request)
				.map(ud -> SecurityUtils.isRoleAuthorityGranted(ud, permissionRoles))
				.orElse(false);
	}

	private static Set<DomainRole> minimalStaffRoles(@Nonnull HttpServletRequest request) {
		return USER_KIND_STAFF_ROLE_SET_MAP.entrySet().stream()
				.filter(e -> e.getKey().isStaffArea(request.getRequestURI()))
				.findFirst()
				.map(Map.Entry::getValue)
				.orElseGet(ImmutableSet::of);
	}

	static List<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<DomainRole> roles) {
		return Optional.ofNullable(roles).orElse(Collections.emptySet())
				.stream()
				.map(SecurityUtils::toAuthority)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	public static Optional<SimpleGrantedAuthority> toAuthority(DomainRole role) {
		return Optional.ofNullable(role)
				.map(DomainRole::getAuthorityName)
				.map(s -> StringUtils.startsWith(s, ROLE_PREFIX) ? s : ROLE_PREFIX + s)
				.filter(s -> !StringUtils.equals(s, ROLE_NAME_VIEWER))
				.map(SimpleGrantedAuthority::new);
	}

	private static boolean isRoleAuthorityGranted(UserDetails userDetails, Collection<DomainRole> checkAgainstRoles) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(
					"\nchecking {} user against roles: {}\nuser authorities: {}",
					userDetails.getUsername(),
					checkAgainstRoles,
					userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())
			);
		}
		final boolean result = checkAgainstRoles
				.stream()
				.anyMatch(rr -> isRoleGrantedToUser(userDetails, rr));
		final String strResult = result ? "" : "not";
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("\nUser {} is {} granted with one of the {} roles", userDetails.getUsername(), strResult, checkAgainstRoles);
		}
		return result;
	}

	private static boolean isRoleGrantedToUser(UserDetails userDetails, DomainRole domainRole) {
		return Optional.ofNullable(userDetails)
				.map(UserDetails::getAuthorities)
				.orElseGet(Collections::emptySet)
				.stream()
				.map(GrantedAuthority::getAuthority)
				.map(DomainRole::parseAuthorityName)
				.anyMatch(gr -> gr.covers(domainRole));
	}
}
