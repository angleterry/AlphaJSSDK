package com.icourt.alpha.alphajssdk.model;

import com.icourt.alpha.alphajssdk.IJavaCallback2JS;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/5/30.
 */
public class Request {

    /**
     * 方法名成的key
     */
    private static String REQUEST_METHOD_NAME = "request_method_name";

    /**
     * 请求ID的key
     */
    private String REQUEST_CALLBACK_ID_NAME = "request_callback_id_name";

    /**
     * 参数的key
     */
    private String REQUEST_PARAMS_NAME = "request_params_name";

    /**
     * 请求的方法名称
     */
    public String methodName;

    /**
     * 请求的ID，一个请求有一个唯一ID，目的是对应回调
     */
    public String callbackId;

    /**
     * 请求的参数
     */
    public JSONObject params;

    /**
     * 请求方法的回调
     */
    public IJavaCallback2JS iJavaCallback2JS;

    public void parseRequest(JSONObject jsonObject) {
        if (jsonObject != null) {
            callbackId = jsonObject.optString(REQUEST_CALLBACK_ID_NAME);
            methodName = jsonObject.optString(REQUEST_METHOD_NAME);
            params = jsonObject.optJSONObject(REQUEST_PARAMS_NAME);
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(REQUEST_CALLBACK_ID_NAME, callbackId);
            jsonObject.put(REQUEST_METHOD_NAME, methodName);
            if (params != null) {
                jsonObject.put(REQUEST_PARAMS_NAME, params);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
