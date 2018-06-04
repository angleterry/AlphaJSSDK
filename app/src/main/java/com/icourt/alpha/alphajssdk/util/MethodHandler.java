package com.icourt.alpha.alphajssdk.util;

import com.icourt.alpha.alphajssdk.model.Params;
import com.icourt.alpha.alphajssdk.model.RequestResponseBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description 通过反射，调用方法
 * @company Beijing icourt
 * @date 2018/5/30.
 */
public class MethodHandler {

    /**
     * 方法所对应的对象实例
     */
    private Object mInstance;
    /**
     * 要调用的方法
     */
    private Method mMethod;
    /**
     * 方法所对应的参数
     */
    private Params mParams;

    public MethodHandler(Object instance, Method method, Params params) {
        mInstance = instance;
        mMethod = method;
        mParams = params;
    }


    /**
     * 构造一个{@link MethodHandler}
     *
     * @param instance
     * @param method
     * @return
     */
    public static MethodHandler createMethodHandler(Object instance, Method method) {
        if (instance == null || method == null) {
            return null;
        }
        Params params = Params.createParams(method);
        return new MethodHandler(instance, method, params);
    }


    /**
     * 开始执行方法
     *
     * @param requestResponseBuilder 包含了方法的参数所对应的参数值，会把参数值依次解析出来，供方法调用
     */
    public void invoke(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder != null) {
            Object[] values = mParams.convertJson2ParamValues(requestResponseBuilder);
            try {
                mMethod.invoke(mInstance, values);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
