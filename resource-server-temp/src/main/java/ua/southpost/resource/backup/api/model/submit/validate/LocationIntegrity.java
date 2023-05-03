/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.LocationForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines integrity constraint for {@link LocationForm}.
 * Created by mchys on 14.09.2018.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocationIntegrityValidator.class)
@Documented
public @interface LocationIntegrity {
	String message() default "err.location.integrity.violated";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
