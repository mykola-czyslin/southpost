/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.web.model.forms.MailForm;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

/**
 * Provides model attributes common for user controller.
 * Created by mchys on 13.03.2018.
 */
@ControllerAdvice(assignableTypes = {UserProfileController.class, UserAdminController.class})
public class UsersControllerAdvise {

	@ModelAttribute(name = "scripts")
	public List<String> scripts() {
		return Arrays.asList("user/profile", "mail");
	}

	@ModelAttribute(name = "mailForm")
	public MailForm mailForm() {
		return new MailForm();
	}

}
