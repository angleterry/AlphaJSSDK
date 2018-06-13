package com.icourt.alpha.alphajssdk.interfaces;

import com.icourt.alpha.alphajssdk.annotation.Param;
import com.icourt.alpha.alphajssdk.annotation.ParamResponseStatus;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/5.
 */
public interface MultiResponseCallback<T> extends SimpleResponseCallback {
    void callback(@ParamResponseStatus("success") boolean status, @Param(needConvert = true) T t);
}
