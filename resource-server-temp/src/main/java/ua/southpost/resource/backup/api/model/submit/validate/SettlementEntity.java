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
 * Defines validation rules for the settlement.
 * Created by mchys on 20.03.2018.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = {SettlementFormValidator.class})
@Documented
public @interface SettlementEntity {

	String message() default "err.invalid.settlement.specification";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
