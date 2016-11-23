package com.hl.common.web.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * controller权限判断使用
 * 
 * @author 
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCfg {

	/** 是否进行权限验证 */
	boolean auth() default true;

	/** 功能名称 */
	String name() default "";

	/** 功能代码 */
	String code() default "";

}
