package com.icourt.alpha.alphajssdk.model;

import com.icourt.alpha.alphajssdk.AlphaJavaJsBridge;
import com.icourt.alpha.alphajssdk.annotation.Param;
import com.icourt.alpha.alphajssdk.annotation.ParamCallback;
import com.icourt.alpha.alphajssdk.annotation.ParamResponseStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/5/30.
 */
public class Params {

    /**
     * 解析出来的所有注解item
     */
    private BaseParamItem[] paramItems;
    private static AlphaJavaJsBridge alphaJavaJsBridge;

    /**
     * 初始化方法
     *
     * @param simpleJavaJsBridge
     */
    public static void init(AlphaJavaJsBridge simpleJavaJsBridge) {
        alphaJavaJsBridge = simpleJavaJsBridge;
    }

    public AlphaJavaJsBridge getSimpleJavaJsBridge() {
        return alphaJavaJsBridge;
    }

    /**
     * 把json转化为参数值
     *
     * @param requestResponseBuilder 包含了一系列的json数据，json数据是request或者response
     * @return
     */
    public Object[] convertJson2ParamValues(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null || paramItems == null) {
            return null;
        }
        Object[] result = new Object[paramItems.length];
        BaseParamItem paramItem;
        for (int i = 0; i < paramItems.length; i++) {
            paramItem = paramItems[i];
            if (paramItem != null) {

                result[i] = paramItem.convertJson2ParamValue(requestResponseBuilder);
            }
        }
        return result;

    }

    /**
     * 把参数值转化为json
     *
     * @param requestResponseBuilder
     * @param paramValues            参数值
     */
    public void convertParamValues2Json(RequestResponseBuilder requestResponseBuilder, Object[] paramValues) {
        if (requestResponseBuilder == null || paramValues == null) {
            return;
        }
        BaseParamItem paramItem = null;
        for (int i = 0; i < paramItems.length; i++) {
            paramItem = paramItems[i];
            if (paramItem != null) {
                paramItem.convertParamValue2Json(requestResponseBuilder, paramValues[i]);
            }
        }
    }

    /**
     * 从{@link Method}中解析它所包含的参数
     *
     * @param method
     * @return
     */
    public static Params createParams(Method method) {
        if (method != null) {
            Annotation[][] annotations = method.getParameterAnnotations();
            Class[] parameters = method.getParameterTypes();
            if (annotations != null) {
                Params params = new Params();
                params.paramItems = new BaseParamItem[annotations.length];
                BaseParamItem paramItem = null;
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = null;
                    if (annotations[i].length == 0) {
                        throw new IllegalArgumentException("方法的所有参数必须都得用" + Param.class.getSimpleName() + "," + ParamCallback.class.getSimpleName() + "," + ParamResponseStatus.class.getSimpleName() + " 中的任意一个注解进行标注");

                    }
                    for (int j = 0; j < annotations[i].length; j++) {
                        annotation = annotations[i][j];
                        if (annotation != null && annotation instanceof Param) {
                            Param paramKey = (Param) annotation;
                            paramItem = new ParamItem(paramKey.value(), parameters[i], paramKey.needConvert());
                            params.paramItems[i] = paramItem;
                        } else if (annotation instanceof ParamCallback) {
                            paramItem = new ParamCallbackItem(parameters[i], null);
                            params.paramItems[i] = paramItem;
                        } else if (annotation instanceof ParamResponseStatus) {
                            ParamResponseStatus paramResponseStatus = (ParamResponseStatus) annotation;
                            paramItem = new ParamResponseStatusItem(parameters[i], paramResponseStatus.value());
                            params.paramItems[i] = paramItem;
                        }
                    }
                }

                return params;
            }

        }
        return null;
    }
}
