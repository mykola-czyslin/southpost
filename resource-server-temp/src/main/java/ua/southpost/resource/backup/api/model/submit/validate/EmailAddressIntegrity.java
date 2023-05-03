/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.api.model.submit.EmailPayload;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Establishes integrity rule for {@link EmailPayload#getEmailAddress()}.
 * Created by mchys on 15.09.2018.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailAddressIntegrityValidator.class)
@Documented
public @interface EmailAddressIntegrity {
	String message() default "err.email.address.integrity.violated";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
