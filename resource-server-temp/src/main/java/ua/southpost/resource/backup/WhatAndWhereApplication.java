package ua.southpost.resource.backup;

import com.google.common.collect.Maps;
import ua.southpost.resource.commons.model.entity.Identity;
import ua.southpost.resource.backup.data.model.UserActivityKind;
import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.service.mail.MailConfig;
import ua.southpost.resource.backup.web.controller.ContextualizeMenuInterceptor;
import ua.southpost.resource.backup.web.model.menu.ApplicationMenuConfig;
import ua.southpost.resource.commons.service.EntityGridSettings;
import ua.southpost.resource.backup.web.security.WebSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ua.southpost.resource.backup.web.ApplicationURI;
import ua.southpost.resource.backup.web.security.DomainRole;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@ComponentScan({
		"net.chyslin.ww.web.*",
		"net.chyslin.ww.service.*",
		"net.chyslin.ww.api.*"
})
@Import({
		ApplicationMenuConfig.class,
		MailConfig.class,
		WebSecurityConfig.class
})
@EnableJpaRepositories(basePackages = {"net.chyslin.ww.data.repo"})
@EntityScan(basePackages = {"net.chyslin.ww.data.model"})
@ImportResource({
		/* web app */ "classpath:spring/page-scripts.xml",
		/* web app */ "classpath:spring/entity-page-list-service-beans.xml",
		/* web app */"classpath:spring/entity-data-service.xml",
		/* api */ "classpath:spring/entity-sort-config.xml",
		/* api */ "classpath:spring/entity-lookup-beans.xml",
		/* api */ "classpath:spring/entity-management-request-handlers.xml"
})
public class WhatAndWhereApplication implements WebMvcConfigurer {
	private static final int CACHE_PERIOD = 31556926;
	private final Logger logger = LoggerFactory.getLogger(WhatAndWhereApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WhatAndWhereApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void logAfterStartup(ApplicationReadyEvent event) {
		logger.info("user home: {}", System.getProperty("user.home"));
		final ConfigurableEnvironment env = event.getApplicationContext().getEnvironment();
		logger.info("database name: {}", env.getProperty("connection.db.name"));
		logger.info("gcp cloud sql database name: {}", env.getProperty("spring.cloud.gcp.sql.database-name"));
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("uk", "UA"));
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates/freemarker");
		freeMarkerConfigurer.setDefaultEncoding("UTF-8");
		Properties settings = new Properties();
		settings.load(getClass().getResourceAsStream("/templates/freemarker-settings.properties"));
		freeMarkerConfigurer.setFreemarkerSettings(settings);
		return freeMarkerConfigurer;
	}

	@Bean
	public ViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(true);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".ftl");
		viewResolver.setContentType("text/html;charset=UTF-8");
		viewResolver.setExposeSpringMacroHelpers(true);
		viewResolver.setExposeRequestAttributes(false);
		viewResolver.setExposeSessionAttributes(false);
		logger.debug("View Resolver just has been configured");
		return viewResolver;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(67108864L);
		logger.debug("Multipart Resolver just has been configured");
		return commonsMultipartResolver;
	}

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
		logger.info("Going to configure Web Server Factory Customizer");
		return (container -> {
			ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/handle-error/400");
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/handle-error/401");
			ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/handle-error/403");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/handle-error/404");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/handle-error/500");
//			container.addErrorPages(error400Page);
			container.addErrorPages(error401Page);
			container.addErrorPages(error403Page);
			container.addErrorPages(error404Page);
			container.addErrorPages(error500Page);
		});
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/web-app");
		messageSource.setUseCodeAsDefaultMessage(true);
		logger.debug("Resource Bundle Message Source configured");
		return messageSource;
	}

	@Bean
	@SessionScope
	public Map<Class<? extends Identity<?>>, Integer> entityPageSizeSessionDictionary() {
		return Maps.newHashMap();
	}

	@Bean
	@SessionScope
	public Map<Class<? extends Identity<?>>, EntityGridSettings> entityGridSettingsDictionary() {
		return Maps.newHashMap();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:static/css/").setCachePeriod(CACHE_PERIOD);
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:static/img/").setCachePeriod(CACHE_PERIOD);
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:static/js/").setCachePeriod(CACHE_PERIOD);
		logger.debug("Resource handlers configured");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
				new ContextualizeMenuInterceptor()
						.addAnonymousOnlyAction(ApplicationURI.AUTHENTICATION_REGISTER)
						.addAnonymousOnlyAction(ApplicationURI.AUTHENTICATION_LOGIN)
						.addAuthenticatedOnlyAction(ApplicationURI.USER_PROFILE_EDIT)
						.addAuthenticatedOnlyAction(ApplicationURI.VACANCY_ADMIN_CREATE)
						.addAuthenticatedOnlyAction(ApplicationURI.DWELLING_CREATE_FORM)
						.addAuthenticatedOnlyAction(ApplicationURI.CLINIC_CREATE_FORM)
						.addAuthenticatedOnlyAction(ApplicationURI.LAWYER_CREATE_FORM)
						.addAuthenticatedOnlyAction(ApplicationURI.AUTHENTICATION_LOGOUT)
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.SUPERVISOR), ApplicationURI.USER_ADMIN_LIST)
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/settlement/list")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/settlement/add")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/streets/list")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/streets/add")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/locations/list")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/locations/add")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/contacts/list")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/contacts/add")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/phones/list")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/phones/add")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/emails/list")
						.addMinimalRoleAction(DomainRole.fromUserRole(UserRole.ADMINISTRATOR), "/master/emails/add")
						.addMinimalRoleAction(DomainRole.fromUserRoleAndKind(UserRole.OPERATOR, UserActivityKind.EMPLOYER), "/employers/list")
						.addMinimalRoleAction(DomainRole.fromUserRoleAndKind(UserRole.ADMINISTRATOR, UserActivityKind.EMPLOYER), "/employers/add")
		);
		logger.debug("Interceptors configured");
	}

}

