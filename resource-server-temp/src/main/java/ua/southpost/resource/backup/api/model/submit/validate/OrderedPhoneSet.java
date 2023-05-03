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
 * Establishes integrity rule over {@link ContactForm#getPhones()}.
 * Created by mchys on 15.09.2018.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OrderedPhoneSetValidator.class)
@Documented
public @interface OrderedPhoneSet {
	String message() default "err.phone.repetition.found";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
