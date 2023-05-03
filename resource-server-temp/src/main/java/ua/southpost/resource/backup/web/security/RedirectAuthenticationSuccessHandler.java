package ua.southpost.resource.backup.web.security;

import ua.southpost.resource.backup.data.model.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;


/**
 * Redirects request to default url.
 * Created by mchys on 14.11.2016.
 */
public class RedirectAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static final String ROLE_PREFIX = "ROLE_";
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private final Map<UserRole, String> roleUrlMapping;
	private final String defaultTarget;
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	RedirectAuthenticationSuccessHandler(Map<UserRole, String> roleUrlMapping, String defaultTarget) {
		this.roleUrlMapping = roleUrlMapping;
		this.defaultTarget = defaultTarget;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
			throws IOException {

		logger.info("*****\ninitial request URL: {}", httpServletRequest.getRequestURI());
		updateSessionAttributes(httpServletRequest, authentication);

		if (httpServletResponse.isCommitted()) {
			logger.info("response is already committed cannot redirect");
			return;
		}

		String targetUrl = determineTargetUrl(authentication);
		logger.info("going to redirect to " + targetUrl);
		redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetUrl);
	}

	private void updateSessionAttributes(@SuppressWarnings("unused") HttpServletRequest request, Authentication authentication) {
		logger.info("updating session attributes. principal = {}", authentication.getPrincipal());
	}

	private String determineTargetUrl(Authentication authentication) {

		return Arrays.stream(UserRole.values())
				.filter(r -> hasRole(authentication, r)).min(Comparator.comparing(r -> -r.ordinal(), Integer::compareTo))
				.map(r -> Optional.ofNullable(roleUrlMapping).orElse(Collections.emptyMap()).get(r))
				.orElse(defaultTarget);
	}

	private boolean hasRole(Authentication authentication, UserRole role) {
		final String effectiveRole = ROLE_PREFIX + role.name();
		return Optional.ofNullable(authentication.getAuthorities())
				.orElse(Collections.emptySet())
				.stream().anyMatch(a -> StringUtils.equalsIgnoreCase(effectiveRole, a.getAuthority()));
	}

}
