package org.sweet.frameworks.foundation.annotation.controller;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sweet.frameworks.foundation.annotation.servlet.Servlet;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Servlet
public @interface RestController{
	String apiService() default "";

	String apiModule() default "";

	String version() default "1.0";

	String value() default "";

	String method() default "GET";

	boolean allowValidated() default false;
}
