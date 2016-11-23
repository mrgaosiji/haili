package com.hl.common.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyCfg {

	/**
	 * 属�?key、propertyName
	 * @return
	 */
	String value();
}
