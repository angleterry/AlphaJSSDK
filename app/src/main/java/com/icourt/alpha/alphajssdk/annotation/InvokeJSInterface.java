package com.icourt.alpha.alphajssdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description native调用JS接口方法的注解
 * @company Beijing icourt
 * @date 2018/5/30.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface InvokeJSInterface {
    String value();
}
