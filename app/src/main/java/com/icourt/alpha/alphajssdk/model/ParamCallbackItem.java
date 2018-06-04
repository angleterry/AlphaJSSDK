package com.icourt.alpha.alphajssdk.model;

import com.icourt.alpha.alphajssdk.exception.SimpleJSBridgeException;
import com.icourt.alpha.alphajssdk.IJavaCallback2JS;
import com.icourt.alpha.alphajssdk.AlphaJavaJsBridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/1.
 */
public class ParamCallbackItem extends BaseParamItem {

    public ParamCallbackItem(Class callbackClass, String paramKey) {
        super(callbackClass, paramKey);
    }

    @Override
    public Object convertJson2ParamValue(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null || requestResponseBuilder.getCallbackId() == null) {
            return null;
        }
        final String resId = requestResponseBuilder.getCallbackId();
        return Proxy.newProxyInstance(paramType.getClassLoader(), new Class<?>[]{paramType},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        RequestResponseBuilder response = new RequestResponseBuilder(false);
                        response.setResponseId(resId);
                        Params params = Params.createParams(method);
                        params.convertParamValues2Json(response, args);
                        if (params.getSimpleJavaJsBridge() != null) {

                            params.getSimpleJavaJsBridge().sendData2JS(response);
                        } else {
                            throw new SimpleJSBridgeException(AlphaJavaJsBridge.class.getName() + "必须得进行初始化");
                        }
                        return new Object();
                    }
                }

        );
    }

    @Override
    public void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) {

        if (requestResponseBuilder == null || obj == null || !(obj instanceof IJavaCallback2JS)) {
            return;
        }
        requestResponseBuilder.setRequestCallback((IJavaCallback2JS) obj);

    }
}
