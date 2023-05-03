package ua.southpost.resource.backup.web.controller;

import com.google.common.collect.ImmutableSet;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.web.model.menu.CatalogMenu;
import ua.southpost.resource.backup.web.security.DomainRole;
import ua.southpost.resource.backup.web.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.copyOf;

/**
 * performs menu contextualization
 * Created by mykola on 15.11.16.
 */
public class ContextualizeMenuInterceptor implements HandlerInterceptor {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<String> authenticatedOnlyActions;
	private List<String> anonymousOnlyActions;
	private Map<DomainRole, Set<String>> minimalRolePrivilegeActions;


	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
		logger.debug("before handle of: {}", httpServletRequest.getRequestURI());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object, ModelAndView modelAndView) {
		if (modelAndView == null) {
			return;
		}
		CatalogMenu menu = (CatalogMenu) modelAndView.getModelMap().get("menu");
		if (menu == null) {
			return;
		}
		Optional<UserDetails> principal = Optional.ofNullable(httpServletRequest.getUserPrincipal())
				.filter(Authentication.class::isInstance)
				.filter(a -> !(a instanceof AnonymousAuthenticationToken))
				.map(p -> (Authentication) p)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.map(p -> (UserDetails) p);

		Optional.ofNullable(authenticatedOnlyActions)
				.orElse(Collections.emptyList())
				.stream()
				.map(menu::findAction)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(a -> a.setEnabled(principal.isPresent()));
		Optional.ofNullable(anonymousOnlyActions)
				.orElse(Collections.emptyList())
				.stream()
				.map(menu::findAction)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(a -> a.setEnabled(!principal.isPresent()));
		final Collection<? extends GrantedAuthority> authorities =
				principal.map(UserDetails::getAuthorities)
						.orElseGet(Collections::emptySet);
		Optional.ofNullable(minimalRolePrivilegeActions).orElseGet(Collections::emptyMap)
				.forEach((key, value) -> applyRolePermission(key, value, authorities, menu));
	}

	private void applyRolePermission(
			DomainRole minimalRole,
			Set<String> actions,
			Collection<? extends GrantedAuthority> authorities, CatalogMenu menu) {
		boolean enabled = isMinimalRoleGranted(minimalRole, authorities);
		Optional.ofNullable(actions).orElseGet(Collections::emptySet)
				.stream()
				.map(menu::findAction)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(a -> a.setEnabled(enabled));
	}

	private boolean isMinimalRoleGranted(DomainRole minimalRole, Collection<? extends GrantedAuthority> authorities) {
		return Arrays.stream(UserRole.values())
				.map(DomainRole.Builder::of)
				.flatMap(b -> b.buildSet(ImmutableSet.copyOf(UserActivityKind.values())).stream())
				.filter(r -> r.covers(minimalRole))
				.map(SecurityUtils::toAuthority)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.anyMatch(authorities::contains);
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
		logger.debug("after handle completion of: {}", httpServletRequest.getRequestURI());
	}

	public ContextualizeMenuInterceptor addMinimalRoleAction(DomainRole role, String action) {
		Set<String> roleActions =
				Optional.ofNullable(minimalRolePrivilegeActions)
						.orElseGet(() -> (minimalRolePrivilegeActions = new LinkedHashMap<>()))
						.computeIfAbsent(role, r -> new HashSet<>());
		roleActions.add(action);
		return this;
	}

	public ContextualizeMenuInterceptor addAuthenticatedOnlyAction(String action) {
		if (StringUtils.isNotBlank(action)) {
			removeAuthenticatedOnlyAction(action);
			authenticatedOnlyActions.add(action);

		}
		return this;
	}

	@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
	public ContextualizeMenuInterceptor removeAuthenticatedOnlyAction(String action) {
		if (StringUtils.isNotBlank(action)) {
			Optional.ofNullable(authenticatedOnlyActions).orElseGet(() -> {
				authenticatedOnlyActions = new ArrayList<>();
				return authenticatedOnlyActions;
			})
					.removeIf(a -> StringUtils.equals(a, action));
		}
		return this;
	}

	public ContextualizeMenuInterceptor addAnonymousOnlyAction(String action) {
		if (StringUtils.isNotBlank(action)) {
			removeAnonimousOnlyAction(action);
			anonymousOnlyActions.add(action);

		}
		return this;
	}

	@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
	public ContextualizeMenuInterceptor removeAnonimousOnlyAction(String action) {
		if (StringUtils.isNotBlank(action)) {
			Optional.ofNullable(anonymousOnlyActions).orElseGet(() -> {
				anonymousOnlyActions = new ArrayList<>();
				return anonymousOnlyActions;
			})
					.removeIf(a -> StringUtils.equals(a, action));
		}
		return this;
	}

}
