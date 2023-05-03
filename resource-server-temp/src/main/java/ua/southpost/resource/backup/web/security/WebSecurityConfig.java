package ua.southpost.resource.backup.web.security;

import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.data.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Web security config.
 * Created by mchys on 26.12.2018.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	private static final String ROLE_VIEWER = UserRole.VIEWER.name();
	private static final String ROLE_SUPERVISOR = UserRole.SUPERVISOR.name();
	private static final String ROLE_ADMINISTRATOR = UserRole.ADMINISTRATOR.name();
	private static final String ROLE_EMPLOYER = DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, UserActivityKind.EMPLOYER).getRoleName();
	private static final String ROLE_PROPERTY_AGENT = DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, UserActivityKind.PROPERTY_AGENT).getRoleName();
	private static final String ROLE_CLINIC_ADVISOR = DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, UserActivityKind.CLINIC_ADVISOR).getRoleName();
	private static final String ROLE_LAWYER_ASSISTANT = DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, UserActivityKind.LAW_ASSISTANT).getRoleName();
	private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Value("${security.token.validity.seconds}")
	private int tokenValiditySeconds = 1209600;
	@Value("${password.encoder.strength}")
	private int encoderStrength;
	@Value("${security.default.authentication.success.url}")
	private String defaultAuthenticationSuccessUrl;
	@Value("${security.supervisor.authentication.success.url}")
	private String supervisorAuthenticationSuccessUrl;
	@Value("${security.administrator.authentication.success.url}")
	private String administratorAuthenticationSuccessUrl;
	@Value("${security.operator.authentication.success.url}")
	private String operatorAuthenticationSuccessUrl;
	@Value("${security.viewer.authentication.success.url}")
	private String viewerAuthenticationSuccessUrl;
	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Resource
	private DataSource dataSource;
	@Resource
	private UserRepository userRepository;

	@Bean
	public AuthenticationProvider authProvider() {
		final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService());

		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(encoderStrength);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoService(userRepository);
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		final Map<UserRole, String> roleUrlMap = new LinkedHashMap<>();
		roleUrlMap.put(UserRole.SUPERVISOR, supervisorAuthenticationSuccessUrl);
		roleUrlMap.put(UserRole.ADMINISTRATOR, administratorAuthenticationSuccessUrl);
		roleUrlMap.put(UserRole.OPERATOR, operatorAuthenticationSuccessUrl);
		roleUrlMap.put(UserRole.VIEWER, viewerAuthenticationSuccessUrl);
		return new RedirectAuthenticationSuccessHandler(roleUrlMap, defaultAuthenticationSuccessUrl);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("\n*********************************************************"
				+ "\n***************** Security Configuration ****************"
				+ "\n*************** Configure Http Access Map ***************"
				+ "\n*********************************************************");
		http
				.authorizeRequests()
				.antMatchers("/user/profile/**").hasRole(ROLE_VIEWER)
				.antMatchers("/user/admin/list").hasRole(ROLE_SUPERVISOR)
				.antMatchers("/employer/**").hasAnyRole(ROLE_EMPLOYER, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/vacancy/manage/**").hasAnyRole(ROLE_EMPLOYER, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/dwelling/manage/**").hasAnyRole(ROLE_PROPERTY_AGENT, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/clinic/manage/**").hasAnyRole(ROLE_CLINIC_ADVISOR, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/lawyer/manage/**").hasAnyRole(ROLE_LAWYER_ASSISTANT, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/employer/save").hasAnyRole(ROLE_EMPLOYER, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/vacancy/save").hasAnyRole(ROLE_EMPLOYER, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/dwelling/save").hasAnyRole(ROLE_PROPERTY_AGENT, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/clinic/save").hasAnyRole(ROLE_CLINIC_ADVISOR, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/lawyer/save").hasAnyRole(ROLE_LAWYER_ASSISTANT, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/**/save").hasAnyRole(ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/employer/**/remove").hasAnyRole(ROLE_EMPLOYER, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/vacancy/**/delete").hasAnyRole(ROLE_EMPLOYER, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/dwelling/**/delete").hasAnyRole(ROLE_PROPERTY_AGENT, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/clinic/**/delete").hasAnyRole(ROLE_CLINIC_ADVISOR, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/lawyer/**/delete").hasAnyRole(ROLE_LAWYER_ASSISTANT, ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/api/**/delete").hasAnyRole(ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/master/**/list").hasAnyRole(ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/master/**/add").hasAnyRole(ROLE_ADMINISTRATOR, ROLE_SUPERVISOR)
				.antMatchers("/history/**").hasAnyRole(ROLE_SUPERVISOR)
				.and()
				.formLogin()
				.loginPage("/user/authentication/login")
				.loginProcessingUrl("/perform-login")
				.usernameParameter("login")
				.passwordParameter("password")
				.failureUrl("/user/authentication/login?error")
				.defaultSuccessUrl("/user/profile/welcome", false)
				.successHandler(successHandler())
				.and()
				.logout()
				.logoutUrl("/perform-logout")
				.logoutSuccessUrl("/after-logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
				.httpBasic()
				.and()
				.csrf()
				.and()
				.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(tokenValiditySeconds)
				.and()
				.authenticationProvider(authProvider())
				.userDetailsService(userDetailsService())
		;
	}

}
