package com.icourt.alpha.alphajssdk.interfaces;

import com.icourt.alpha.alphajssdk.annotation.Param;
import com.icourt.alpha.alphajssdk.annotation.ParamResponseStatus;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/5.
 */
public interface SimpleResponseCallback<T> {

    /**
     *  没有参数的回调接口
     * @param success  是否成功
     * @param msg 失败返回消息
     */
    void callbackResponse(@ParamResponseStatus("sucess") boolean success, @Param("content") T t);

}
