package ua.southpost.resource.backup.web.service.pagemodel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ModelAttributeAliases {
	ModelAttributeAlias[] value() default {};
}
