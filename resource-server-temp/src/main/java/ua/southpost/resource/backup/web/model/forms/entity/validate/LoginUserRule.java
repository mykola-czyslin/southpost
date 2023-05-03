package ua.southpost.resource.backup.web.model.forms.entity.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines validation rule that requires proper login/password.
 * Created by mykola on 26.10.16.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LoginUserRuleValidator.class)
@Documented
public @interface LoginUserRule {
	String message() default "err.login.failure";

	String loginProperty() default "login";

	String passwordProperty() default "password";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
