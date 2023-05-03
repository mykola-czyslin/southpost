/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms;

import ua.southpost.resource.backup.web.model.forms.entity.validate.EmailNotUsed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * provides an additional edit field: email.
 * Created by mchys on 11.03.2018.
 */
@EmailNotUsed
public abstract class EditMailAddressUserForm extends AbstractUserForm {
	@NotEmpty(message = "err.empty.email")
	@Email(message = "err.invalid.email.format")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
