/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.VacancyForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines entiti constraint over {@link VacancyForm}.
 * Created by mchys on 24.03.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = {VacancyEntityValidator.class})
@Documented
public @interface VacancyEntity {
	String message() default "err.invalid.vacancy.specification";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
