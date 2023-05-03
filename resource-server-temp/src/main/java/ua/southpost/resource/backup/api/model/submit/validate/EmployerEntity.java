/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines validation rule for employer name.
 * Created by mchys on 20.03.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = {EmployerEntityValidator.class})
@Documented
public @interface EmployerEntity {
	String message() default "err.employer.entity.invalid";

	String idProperty();

	String nameProperty();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
