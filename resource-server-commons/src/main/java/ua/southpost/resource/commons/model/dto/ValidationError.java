/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.commons.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Locale;
import java.util.Objects;

/**
 * Container those allows perform validation error transfer back to the client.
 * Created by mchys on 31.03.2018.
 */
public class ValidationError {
	@JsonProperty("caused_by")
	private String causedBy;
	@JsonProperty("message")
	private String message;

	public static ValidationError fromObjectError(ObjectError error, MessageSource messageSource, Locale locale) {
		final ValidationError validationError = new ValidationError();
		if (error instanceof FieldError) {
			validationError.setCausedBy(((FieldError) error).getField());
		} else {
			validationError.setCausedBy(error.getObjectName());
		}
		validationError.setMessage(messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), error.getArguments(), locale));
		return validationError;
	}

	@SuppressWarnings("unused")
	public String getCausedBy() {
		return causedBy;
	}

	private void setCausedBy(String causedBy) {
		this.causedBy = causedBy;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
