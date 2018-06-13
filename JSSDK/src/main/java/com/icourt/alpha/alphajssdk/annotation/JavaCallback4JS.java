package com.icourt.alpha.alphajssdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description native调用js方法时，同时传入的回调方法。js运行完之后通过该回调给native返回结果。标注回调方法
 * @company Beijing icourt
 * @date 2018/5/30.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface JavaCallback4JS {

}
