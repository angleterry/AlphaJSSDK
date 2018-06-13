package com.icourt.alpha.alphajssdk.model;

import android.text.TextUtils;
import com.icourt.alpha.alphajssdk.IJavaCallback2JS;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/5/30.
 */
public class RequestResponseBuilder {

    /**
     * 是否是构建request请求
     */
    private boolean buildRequest;

    /**
     * 请求数据
     */
    private Request request;
    /**
     * 响应数据
     */
    private Response response;

    public RequestResponseBuilder(boolean isBuildRequest) {
        this(isBuildRequest, null);
    }

    /**
     * @param isBuildRequest 是否是构造请求数据
     * @param data           json数据
     */
    public RequestResponseBuilder(boolean isBuildRequest, JSONObject data) {
        buildRequest = isBuildRequest;
        if (buildRequest) {
            request = new Request();
            if (data != null) {
                request.parseRequest(data);
            }
        } else {
            response = new Response();
            if (data != null) {
                response.parseResponse(data);
            }
        }
    }


    /**
     * 请求数据格式：
     * <p>
     * <pre>
     *    {
     *      "handlerName":"test",
     *      "callbackId":"c_111111",
     *      "params":{
     *          ....
     *      }
     *    }
     *    hanlerName 代表java与js之间给对方暴漏的接口的名称，
     *    callbackId 代表对方在发起请求时，会为回调方法生产一个唯一的id值，它就代表这个唯一的id值
     *    params     代表传递的数据
     * </pre>
     * }
     */
    private static class Request {

        private static String sRequestInterfaceName = "handlerName";
        private static String sRequestCallbackIdName = "callbackId";
        private static String sRequestValuesName = "params";

        private String interfaceName;
        private String callbackId;
        private JSONObject requestValues;
        private IJavaCallback2JS iJavaCallback2JS;

        private static void init(String requestInterfaceName, String requestCallbackIdName, String requestValuesName) {
            if (!TextUtils.isEmpty(requestCallbackIdName)) {
                Request.sRequestCallbackIdName = requestCallbackIdName;
            }

            if (!TextUtils.isEmpty(requestValuesName)) {
                Request.sRequestValuesName = requestValuesName;
            }
            if (!TextUtils.isEmpty(requestInterfaceName)) {
                Request.sRequestInterfaceName = requestInterfaceName;
            }
        }

        private void parseRequest(JSONObject json) {
            if (json != null) {
                callbackId = json.optString(sRequestCallbackIdName);
                interfaceName = json.optString(sRequestInterfaceName);
                requestValues = json.optJSONObject(sRequestValuesName);
            }
        }

        @Override
        public String toString() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(sRequestCallbackIdName, callbackId);
                jsonObject.put(sRequestInterfaceName, interfaceName);
                if (requestValues != null) {
                    jsonObject.put(sRequestValuesName, requestValues);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "'" + jsonObject.toString() + "'";
        }
    }

    /**
     * response数据格式：
     * <pre>
     *  {
     *      "responseId":"iii",
     *      "data":{
     *          "status":"1",
     *          "msg":"ok",
     *          "values":{
     *              ......
     *          }
     *      }
     *  }
     *
     *  responseId 代表request中的callbackId
     *  data       代表响应的数据
     *  status     代表响应状态
     *  msg        代表响应状态对应的消息
     *  values     代表响应数据包含的值
     * </pre>
     */
    private static class Response {
        private static String sResponseIdName = "responseId";
        private static String sResponseValuesName = "values";
        private static String sResponseName = "data";

        private String responseId;
        private JSONObject response = new JSONObject();
        private JSONObject responseValues;

        private static void init(String responseIdName, String responseName, String responseValuesName) {
            if (!TextUtils.isEmpty(responseValuesName)) {

                Response.sResponseValuesName = responseValuesName;
            }
            if (!TextUtils.isEmpty(responseIdName)) {
                Response.sResponseIdName = responseIdName;
            }

            if (!TextUtils.isEmpty(responseName)) {

                Response.sResponseName = responseName;
            }

        }

        private void parseResponse(JSONObject json) {
            if (json != null) {
                responseId = json.optString(sResponseIdName);
                response = json.optJSONObject(sResponseName);
                if (response != null) {
                    responseValues = response.optJSONObject(sResponseValuesName);
                }
            }
        }

        @Override
        public String toString() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(sResponseIdName, responseId);
                if (responseValues != null) {
                    response.put(sResponseValuesName, responseValues);
                }
                jsonObject.put(sResponseName, response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "'" + jsonObject.toString() + "'";
        }
    }

    /**
     * 获取请求时为回调函数生成的 callbackId
     *
     * @return
     */
    public String getCallbackId() {
        return request == null ? null : request.callbackId;
    }

    /**
     * 获取请求的接口的名字
     *
     * @return
     */
    public String getInterfaceName() {
        return request == null ? null : request.interfaceName;
    }

    public void setRequestCallback(IJavaCallback2JS callback) {
        initRequest();
        this.request.iJavaCallback2JS = callback;
    }

    private void initRequest() {
        if (request == null) {
            request = new Request();
        }
    }

    /**
     * 设置请求的接口的名字
     *
     * @param interfaceName
     */
    public void setInterfaceName(String interfaceName) {
        initRequest();
        this.request.interfaceName = interfaceName;
    }

    /**
     * 为回调方法设置回调id
     *
     * @param callbackId
     */
    public void setCallbackId(String callbackId) {
        initRequest();
        this.request.callbackId = callbackId;
    }


    /**
     * 获取request或者response的 values值
     *
     * @return
     */
    public JSONObject getValues() {
        if (buildRequest) {
            return request == null ? null : request.requestValues;
        } else {
            return response == null ? null : response.responseValues;
        }
    }

    /**
     * 往request或者response中存放 数据
     *
     * @param key
     * @param value
     */
    public void putValue(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }
        JSONObject values = null;
        if (buildRequest) {
            initRequest();
            if (request.requestValues == null) {
                request.requestValues = new JSONObject();
            }
            values = request.requestValues;
        } else {
            initResponse();
            if (response.responseValues == null) {
                response.responseValues = new JSONObject();
            }
            values = response.responseValues;
        }

        try {
            values.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public IJavaCallback2JS getCallback() {
        return request == null ? null : request.iJavaCallback2JS;
    }

    /**
     * @param responseIdName
     * @param responseName
     * @param responseValuesName
     */
    public static void init(String responseIdName, String responseName, String responseValuesName, String requestInterfaceName, String requestCallbackIdName, String requestValuesName) {
        Response.init(responseIdName, responseName, responseValuesName);
        Request.init(requestInterfaceName, requestCallbackIdName, requestValuesName);
    }


    private void initResponse() {
        if (response == null) {
            response = new Response();
        }
    }

    public String getResponseId() {
        return response == null ? null : response.responseId;
    }

    public void setResponseId(String responseId) {
        initResponse();
        this.response.responseId = responseId;
    }

    /**
     * 获取response的 状态数据
     *
     * @return
     */
    public JSONObject getResponseStatus() {
        return response == null ? null : response.response;
    }

    /**
     * 往response中存放 数据
     *
     * @param key
     * @param value
     */
    public void putResponseStatus(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }
        initResponse();
        try {
            response.response.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从json中创建一个{@link RequestResponseBuilder}对象，其实最终创建的是一个 request或者response
     *
     * @param json
     * @return
     */
    public static RequestResponseBuilder create(JSONObject json) {
        if (json == null) {
            return null;
        }
        RequestResponseBuilder requestResponseBuilder = null;
        /*响应数据*/
        if (json.has(Response.sResponseIdName)) {
            requestResponseBuilder = new RequestResponseBuilder(false, json);
        } else {
            requestResponseBuilder = new RequestResponseBuilder(true, json);
        }

        return requestResponseBuilder;
    }

    /**
     * 是否构建的时request数据
     *
     * @return
     */
    public boolean isBuildRequest() {
        return buildRequest;
    }

    @Override
    public String toString() {
        if (buildRequest) {
            return request == null ? super.toString() : request.toString();
        } else {
            return response == null ? super.toString() : response.toString();
        }
    }
}
