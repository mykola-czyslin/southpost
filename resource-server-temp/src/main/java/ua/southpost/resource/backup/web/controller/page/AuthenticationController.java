/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.service.user.UserPermissionRequestNotificationService;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.forms.RegisterUserForm;
import ua.southpost.resource.backup.web.service.user.UserRegistrationRequest;
import ua.southpost.resource.backup.web.service.user.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static ua.southpost.resource.backup.web.controller.ControllerConstants.CONTAINER_PAGE_VIEW;
import static ua.southpost.resource.backup.web.controller.ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE;
import static ua.southpost.resource.backup.web.controller.page.ModelAttributeConstants.ERROR_ATTRIBUTE_NAME;
import static ua.southpost.resource.backup.web.controller.page.ModelAttributeConstants.SCRIPTS_ATTRIBUTE_NAME;

/**
 * dispathes authentication requests
 * Created by mykola on 12.11.16.
 */
@Controller
@RequestMapping("/user/authentication")
public class AuthenticationController {
	private static final String USERS_REGISTER_VIEW_PATH = "users/register";
	private static final String USERS_LOGIN_VIEW_PATH = "users/login";
	private static final String TITLE_AUTHENTICATION = "title.authentication";
	private static final String REDIRECT_VIEW = "redirect:/";
	protected static final String COMMAND_ATTRIBUTE_NAME = "command";
	protected static final String CANNOT_REGISTER_ERROR_MESSAGE_KEY = "err.cannot.register";
	protected static final List<String> REGISTER_PAGE_SCRIPTS = Collections.singletonList("user/register");
	protected static final String REGISTER_FORM_ATTRIBUTE_NAME = "registerForm";
	protected static final String AUTHENTICATION_ERROR_MESSAGE_KEY = "message.authentication.error";
	@Resource
	private UserRegistrationService registrationService;
	@Resource
	private UserPermissionRequestNotificationService userPermissionRequestNotificationService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@SuppressWarnings("SameReturnValue")
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView(CONTAINER_PAGE_VIEW);
		modelAndView.addObject(REGISTER_FORM_ATTRIBUTE_NAME, new RegisterUserForm());
		modelAndView.addObject(SCRIPTS_ATTRIBUTE_NAME, REGISTER_PAGE_SCRIPTS);
		modelAndView.addObject(CONTENT_VIEW_MODEL_ATTRIBUTE, USERS_REGISTER_VIEW_PATH);
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute(REGISTER_FORM_ATTRIBUTE_NAME) @Valid RegisterUserForm registerUserForm, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(CONTAINER_PAGE_VIEW);
		if (bindingResult.hasErrors()) {
			modelAndView.addObject(COMMAND_ATTRIBUTE_NAME, registerUserForm);
			modelAndView.addObject(SCRIPTS_ATTRIBUTE_NAME, REGISTER_PAGE_SCRIPTS);
			modelAndView.addObject(CONTENT_VIEW_MODEL_ATTRIBUTE, USERS_REGISTER_VIEW_PATH);
			return modelAndView;
		}
		final String login = registerUserForm.getLogin();
		final String password = registerUserForm.getPassword();
		final UserRegistrationRequest registrationRequest
				= UserRegistrationRequest.builder()
						  .email(registerUserForm.getEmail())
						  .login(registerUserForm.getPassword())
						  .firstName(registerUserForm.getFirstName())
						  .lastName(registerUserForm.getLastName())
						  .password(registerUserForm.getPassword())
						  .declaredActivities(registerUserForm.getUserActivityKinds())
						  .build();
		final DetailedUserInfo user = registrationService.register(registrationRequest, request.getLocale());
		if (user.getId() != null) {
			handleRegistrationSuccess(request, login, password, user);
			return new ModelAndView(REDIRECT_VIEW);
		} else {
			bindingResult.addError(new ObjectError(COMMAND_ATTRIBUTE_NAME, CANNOT_REGISTER_ERROR_MESSAGE_KEY));
			modelAndView.addObject(COMMAND_ATTRIBUTE_NAME, registerUserForm);
			modelAndView.addObject(CONTENT_VIEW_MODEL_ATTRIBUTE, USERS_REGISTER_VIEW_PATH);
			return modelAndView;
		}
	}

	private void handleRegistrationSuccess(HttpServletRequest request, String login, String password, DetailedUserInfo user) {
		userPermissionRequestNotificationService.sendPermissionRequiredNotification(user, request.getLocale());
		try {
			request.login(login, password);
		} catch (ServletException e) {
			logger.error("Fail to perform auto-login after registration for user [" + login + "]", e);
		}
	}

	@SuppressWarnings("SameReturnValue")
	@GetMapping(value = "/login", params = "!error")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView(CONTAINER_PAGE_VIEW);
		modelAndView.addObject(SCRIPTS_ATTRIBUTE_NAME, Collections.singletonList("user/login"));
		modelAndView.addObject(CONTENT_VIEW_MODEL_ATTRIBUTE, USERS_LOGIN_VIEW_PATH);
		return modelAndView;
	}

	@SuppressWarnings("SameReturnValue")
	@GetMapping(value = "/login", params = "error")
	public ModelAndView loginError(@Nonnull Model model) {
		ModelAndView modelAndView = new ModelAndView(CONTAINER_PAGE_VIEW);
		modelAndView.addAllObjects(model.asMap());
		modelAndView.addObject(ERROR_ATTRIBUTE_NAME, AUTHENTICATION_ERROR_MESSAGE_KEY);
		modelAndView.addObject(SCRIPTS_ATTRIBUTE_NAME, Collections.singletonList("user/login"));
		modelAndView.addObject(CONTENT_VIEW_MODEL_ATTRIBUTE, USERS_LOGIN_VIEW_PATH);
		return modelAndView;
	}


	@SuppressWarnings("SameReturnValue")
	@ModelAttribute("title")
	public String title() {
		return TITLE_AUTHENTICATION;
	}

}
