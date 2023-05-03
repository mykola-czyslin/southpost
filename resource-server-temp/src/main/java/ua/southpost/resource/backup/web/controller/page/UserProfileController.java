/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.web.GenericWebException;
import ua.southpost.resource.backup.web.controller.ControllerConstants;
import ua.southpost.resource.backup.web.controller.service.UserCredentialsService;
import ua.southpost.resource.backup.web.controller.service.UserProfileService;
import ua.southpost.resource.commons.model.dto.AbstractEntityInfo;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.forms.entity.UpdateUserCredentialsForm;
import ua.southpost.resource.backup.web.model.forms.entity.UpdateUserProfileForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.service.entity.UserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Profile controller
 * Created by mykola on 14.11.16.
 */
@Controller
@RequestMapping("/user/profile")
public class UserProfileController {
	private static final String TITLE_PROFILE = "title.profile";
	@Resource(name = "localUserDataService")
	private UserDataService userDataService;
	@Resource
	private EntityFormFactory<Long, DetailedUserInfo, UpdateUserCredentialsForm> updateUserCredentialsFormFactory;
	@Resource
	private EntityFormFactory<Long, DetailedUserInfo, UpdateUserProfileForm> updateUserProfileFormFactory;
	@Resource
	private UserCredentialsService userCredentialsService;
	@Resource
	private UserProfileService userProfileService;


	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView viewProfile(HttpServletRequest servletRequest) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		DetailedUserInfo user = resolveUser(servletRequest);
		modelAndView.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "users/edit-profile");
		modelAndView.addObject("userForm", updateUserProfileFormFactory.formFromEntity(user));
		modelAndView.addObject("roles",
				user.getRoles()
						.stream()
						.map(AbstractEntityInfo::getId)
						.sorted(Comparator.comparing(UserRole::ordinal))
						.collect(Collectors.toList())
		);
		return modelAndView;
	}

	@PostMapping(value = "update")
	public ModelAndView updateProfile(@ModelAttribute("userForm") @Valid UpdateUserProfileForm userForm, BindingResult bindingResult) {

		final ModelAndView modelAndView = new ModelAndView(ControllerConstants.AJAX_PROFILE_FORM_RESPONSE_VIEW);
		if (bindingResult.hasErrors()) {
			return modelAndView;
		} else {
			userProfileService.updateProfile(userForm);
		}
		return modelAndView;
	}

	@RequestMapping(value = "change-password", method = RequestMethod.GET)
	public ModelAndView changePassword(HttpServletRequest servletRequest) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		modelAndView.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "users/change-password");
		DetailedUserInfo user = resolveUser(servletRequest);
		modelAndView.addObject("editUserForm", updateUserCredentialsFormFactory.formFromEntity(user));
		return modelAndView;
	}

	@RequestMapping(value = "change-password", method = RequestMethod.POST)
	public ModelAndView changePassword(@ModelAttribute("editUserForm") @Valid UpdateUserCredentialsForm userCredentialsForm,
									   BindingResult bindingResult) {
		ModelAndView modelAndView;
		if (bindingResult.hasErrors()) {
			modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
			modelAndView.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "users/change-password");
		} else {
			userCredentialsService.updateCredentials(userCredentialsForm.getUserId(), userCredentialsForm.getOldPassword(), userCredentialsForm.getPassword());
			modelAndView = new ModelAndView(new RedirectView("/welcome"));
		}
		return modelAndView;
	}

	@GetMapping("/welcome")
	public ModelAndView welcome(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		modelAndView.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "users/welcome");
		DetailedUserInfo user = resolveUser(request);
		modelAndView.addObject("user", user);
		return modelAndView;
	}


	private DetailedUserInfo resolveUser(HttpServletRequest request) {
		String login = Optional.ofNullable(request.getUserPrincipal())
				.map(Principal::getName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		return userDataService.byLogin(login, request.getLocale())
				.orElseThrow(() -> new GenericWebException(String.format("User '%s' isn't registered", login)));
	}

	@SuppressWarnings("SameReturnValue")
	@ModelAttribute(name = "title")
	public String title() {
		return TITLE_PROFILE;
	}

}
