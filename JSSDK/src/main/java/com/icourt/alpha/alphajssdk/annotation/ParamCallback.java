package com.icourt.alpha.alphajssdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description 定义js调用native或者native调用js方法时传入的回调参数
 * @company Beijing icourt
 * @date 2018/5/30.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ParamCallback {

}
