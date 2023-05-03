/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.security.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * Default request dispatcher
 * Created by mykola on 14.10.16.
 */
@Controller
public class DefaultController {
	private static final String HELLO = "hello";
	private static final String MAIN = "main";
	private static final String REDIRECT_AFTER_LOGOUT = "redirect:/after-logout";
	private static final String TITLE_WELCOME = "title.welcome";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private PersistentTokenRepository persistentTokenRepository;

	@SuppressWarnings("SameReturnValue")
	@RequestMapping(value = "/hello/{name}/", method = RequestMethod.GET)
	public String hello(@PathVariable("name") String name, Model model) {
		model.addAttribute("user", StringUtils.defaultString(name, "nobody"));
		return HELLO;
	}


	@SuppressWarnings("SameReturnValue")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "landing");
		return MAIN;
	}

	@RequestMapping(value = "/handle-error/{code}")
	public ModelAndView forbidden(@PathVariable("code") int code,
	                              @RequestAttribute("javax.servlet.forward.request_uri") String forwardRequestUri) {
		ModelAndView error = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		error.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "generic-error");
		error.addObject("error", code);
		error.addObject("timestamp", new Date());
		HttpStatus status;
		try {
			status = HttpStatus.valueOf(code);
		} catch (IllegalArgumentException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		error.addObject("status", status.getReasonPhrase());
		error.addObject("code", code);
		error.addObject("message", "when requested to " + forwardRequestUri);
		return error;
	}

	@SuppressWarnings("SameReturnValue")
	@RequestMapping("/perform-logout")
	public String performLogout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityUtils.resolvePrincipal(request).ifPresent(
				p -> {
					new SecurityContextLogoutHandler().logout(request, response, auth);
					persistentTokenRepository.removeUserTokens(p.getUsername());
				}
		);
		SecurityContextHolder.getContext().setAuthentication(null);
		return REDIRECT_AFTER_LOGOUT;
	}

	@SuppressWarnings("SameReturnValue")
	@RequestMapping("/after-logout")
	public String logout(Model model) {
		logger.info("Handling /logout request with model {}", model);
		model.addAttribute(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "logout");
		return MAIN;
	}

	@SuppressWarnings("SameReturnValue")
	@ModelAttribute(name = "title")
	String title() {
		return TITLE_WELCOME;
	}

}
