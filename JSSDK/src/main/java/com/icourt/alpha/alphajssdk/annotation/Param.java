package com.icourt.alpha.alphajssdk.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description 定义参数，仿Retrofit.利用该注解解析json数据
 * @company Beijing icourt
 * @date 2018/5/30.
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Param {

    /**
     * <pre>json中一般是以{key:value, key1:value1}的格式组织数据，</pre>
     * <p>{@link #value()}就代表key，key1这些值，@Param标注的参数或类的实例属性代表value或value1这些值；</p>
     * {@link #value()}的值可以不设置，但这种情况是基于{@link #needConvert()}为true的条件下，即被{@link Param}标注的属性不能直接存放在json中；
     * 其他情况建议设置{@link #value()}的值
     *
     * @return
     */
    String value() default "";

    /**
     * <pre>json中一般是以{key:value, key1:value1}的格式组织数据，</pre>
     * java中的{@link org.json.JSONObject}类可以存放 {@link org.json.JSONObject}, {@link JSONArray}, String, Boolean,
     * Integer, Long, Double,  or {@code null}这些类，但是对于非以上的类，就得需要进行一些转换才可以往json中存放，
     * 因此对于类的类型不是{@link org.json.JSONObject}可以存放的类型时，{@link #needConvert()}的值设为true，
     * 同时该类需要被放入json中的实例属性要用{@link Param}进行标注,该类必须有一个无参构造函数
     *
     * @return
     */
    boolean needConvert() default false;
}
