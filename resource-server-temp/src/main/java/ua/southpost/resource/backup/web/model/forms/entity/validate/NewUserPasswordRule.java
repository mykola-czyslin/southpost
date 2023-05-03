package ua.southpost.resource.backup.web.model.forms.entity.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines validation rule for new user.
 * Created by mykola on 25.10.16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NewUserPasswordValidator.class)
public @interface NewUserPasswordRule {
	String message() default "err.password.mismatches.confirmation";

	String passwordProperty() default "password";

	String confirmPasswordProperty() default "passwordConfirmation";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
