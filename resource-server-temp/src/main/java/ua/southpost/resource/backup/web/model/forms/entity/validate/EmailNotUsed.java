package ua.southpost.resource.backup.web.model.forms.entity.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares rule that email specified isn't alredy used.
 * Created by mykola on 25.10.16.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotUsedValidator.class)
@Documented
public @interface EmailNotUsed {
	String message() default "err.email.already.in.use";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
