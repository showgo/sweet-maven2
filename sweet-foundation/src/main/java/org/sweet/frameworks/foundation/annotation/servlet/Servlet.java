package org.sweet.frameworks.foundation.annotation.servlet;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Servlet
 * @filename:Servlet
 * @filedescription:
 * @version:1.0.0
 * @author:wugz
 * @finisheddate:2016-4-14
 * @modifyrecords:
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Servlet{
	/* Servlet的名称 */
	String value() default "";
	/* Servlet的url-pattern */
	// String value() default "";

	/* Servlet是否验证会话 */
	boolean allowValidated() default true;

	/* Servlet的描述 */
	String description() default "";
}
