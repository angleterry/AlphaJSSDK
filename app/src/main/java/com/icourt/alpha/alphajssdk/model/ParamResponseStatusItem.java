package com.icourt.alpha.alphajssdk.model;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/1.
 */
public class ParamResponseStatusItem extends BaseParamItem {
    public ParamResponseStatusItem(Class paramClass, String paramKey) {
        super(paramClass, paramKey);
    }

    @Override
    public Object convertJson2ParamValue(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null || requestResponseBuilder.getResponseStatus() == null) {
            return null;
        }
        return requestResponseBuilder.getResponseStatus().opt(paramKey);
    }

    @Override
    public void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) {

        if (requestResponseBuilder == null || obj == null) {
            return;
        }
        requestResponseBuilder.putResponseStatus(paramKey, obj);
    }
}
