/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.api.model.submit.validate;

import ua.southpost.resource.backup.web.model.forms.entity.SettlementForm;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Establishes integrity rule for {@link SettlementForm}.
 * Created by mchys on 15.09.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = SettlementIntegrityValidator.class)
@Documented
public @interface SettlementIntegrity {
	String message() default "err.settlement.integrity.violation";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
