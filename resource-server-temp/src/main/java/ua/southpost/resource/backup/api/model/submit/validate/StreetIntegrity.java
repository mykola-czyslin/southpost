/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.StreetForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Establishes a data integrity rule for {@link StreetForm}.
 * Created by mchys on 15.09.2018.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StreetIntegrityValidator.class)
@Documented
public @interface StreetIntegrity {
	String message() default "err.street.integrity.violated";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
