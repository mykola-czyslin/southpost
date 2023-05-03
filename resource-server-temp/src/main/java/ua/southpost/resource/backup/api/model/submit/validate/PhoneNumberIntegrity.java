/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.PhoneForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines integrity rule for the {@link PhoneForm}.
 * Created by mchys on 15.09.2018.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberIntegrityValidator.class)
@Documented
public @interface PhoneNumberIntegrity {
	String message() default "err.phone.number.integrity.violated";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
