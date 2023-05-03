/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.data.model.UserRole;
import ua.southpost.resource.backup.service.user.UserPermissionManagementService;
import ua.southpost.resource.backup.web.controller.service.PageListService;
import ua.southpost.resource.backup.web.controller.util.EnumOptionsUtil;
import ua.southpost.resource.backup.web.model.dto.user.DetailedUserInfo;
import ua.southpost.resource.backup.web.model.forms.entity.UserPermissionManagementForm;
import ua.southpost.resource.backup.web.model.forms.entity.factory.EntityFormFactory;
import ua.southpost.resource.backup.web.model.forms.search.UserSearchForm;
import ua.southpost.resource.backup.web.model.forms.search.UserSearchFormImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.southpost.resource.backup.web.controller.ControllerConstants;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;

/**
 * dispatches requests related to user management
 * Created by mykola on 16.10.16.
 */
@Controller
@RequestMapping("/user/admin")
public class UserAdminController {
	private static final int[] MARKER_ADMIN = new int[0];
	private static final String TITLE_USERS = "title.users";
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Resource(name = "userPermissionFormFactory")
	private EntityFormFactory<Long, DetailedUserInfo, UserPermissionManagementForm> formFactory;
	@Resource
	private UserPermissionManagementService permissionManagementService;
	@Resource(name = "userPageListService")
	private PageListService<UserSearchForm, DetailedUserInfo, Long> pageListService;
	@Resource
	private EnumOptionsUtil enumOptionsUtil;

	@GetMapping(value = "/list")
	public ModelAndView list(@Nonnull HttpServletRequest request) {
		return pageListService.prepareListModelAndView(null, request.getLocale());
	}

	@PostMapping(value = "/list")
	public ModelAndView list(@Nonnull HttpServletRequest request, @NotNull @ModelAttribute(PageListService.MA_SEARCH_FORM) UserSearchFormImpl searchForm, BindingResult bindingResult) {
		final ModelAndView modelAndView = pageListService.prepareListModelAndView(searchForm, request.getLocale());
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().stream()
					.filter(e -> e instanceof FieldError)
					.map(FieldError.class::cast)
					.forEach(e -> logger.info(String.format("\nField: %1$s,\nRejected Value: %2$s,\nIs binding failure?: %3$s,\nTotal: %4$s ", e.getField(), e.getRejectedValue(), e.isBindingFailure(), e)));
			logger.info("There are some binding errors");
		}
		return modelAndView;
	}

	@GetMapping(value = "/{user_id}/manage-roles")
	public ModelAndView manageRoles(@PathVariable(name = "user_id") long userId, Locale locale) {

		final UserPermissionManagementForm userAdminForm = formFactory.formByEntityId(userId, locale);
		//
		ModelAndView mnv = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		mnv.addObject("editUserForm", userAdminForm);
		mnv.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "users/manage-roles");
		return mnv;
	}

	@PostMapping(value = "/manage-roles")
	public ModelAndView manageRoles(
			@ModelAttribute("editUserForm") @Valid UserPermissionManagementForm userForm,
			Locale locale,
			BindingResult bindingResult) {
		ModelAndView mnv = new ModelAndView(ControllerConstants.CONTAINER_PAGE_VIEW);
		if (bindingResult.hasErrors()) {
			mnv.setViewName(ControllerConstants.CONTAINER_PAGE_VIEW);
			mnv.addObject(ControllerConstants.CONTENT_VIEW_MODEL_ATTRIBUTE, "users/manage-roles");
		} else {
			this.permissionManagementService.updateUserPermissions(userForm.getUserId(), userForm.getRoles(), userForm.getConfirmedUserActivities(), locale);
			mnv.setView(new RedirectView("../list"));
		}
		return mnv;
	}

	@ModelAttribute(name = "roleOptions")
	private Map<String, String> addRoleOptions(HttpServletRequest request) {
		return enumOptionsUtil.options(UserRole.class, request.getLocale(), UserRole::getMessageKey, Comparator.comparing(UserRole::ordinal));
	}

	@SuppressWarnings("SameReturnValue")
	@ModelAttribute(name = "title")
	String title() {
		return TITLE_USERS;
	}

	@SuppressWarnings("unused")
	@ModelAttribute(name = "admin")
	int[] adminAttr() {
		return MARKER_ADMIN;
	}
}
