/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.EmployerForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Establishes the integrity rule for the {@link EmployerForm#getName()}
 * property.
 * Created by mchys on 15.09.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = EmployerNameIntegrityValidator.class)
@Documented
public @interface EmployerNameIntegrity {
	String message() default "err.employer.name.integrity.violated";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
