package com.icourt.alpha.alphajssdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description 回调时传入的json的key值，比如{success:false, msg:"ok"}
 * @company Beijing icourt
 * @date 2018/5/30.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ParamResponseStatus {

    String value() default "";

    boolean responseBKey() default false;
}
