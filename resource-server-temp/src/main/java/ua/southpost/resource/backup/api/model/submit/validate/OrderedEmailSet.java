/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.ContactForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Establishes integrity rule for the value of the {@link ContactForm#getEmails()}.
 * Created by mchys on 15.09.2018.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderedEmailSetValidator.class)
@Documented
public @interface OrderedEmailSet {
	String message() default "err.email.repetition.found";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
