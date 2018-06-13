package com.icourt.alpha.alphajssdk.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description native调用js方法后的返回结果
 * 比如{"responseId":"4_1527669220592","response":{"status":"1","msg":"ok","data":{"city":{"cityName":"长治","cityProvince":"山西"}}}}
 * @company Beijing icourt
 * @date 2018/5/30.
 */
public class Response {

    public static String RESPONSE_ID_NAME = "responseId";

    private static String RESPONSE_NAME = "response";

    private static String RESPONSE_MODEL_NAME = "data";

    /**
     * responseId 唯一ID
     */
    public String responseId;

    /**
     * 返回内容里的response数据
     */
    public JSONObject response = new JSONObject();

    /**
     * 返回内容里的data中的数据
     */
    public JSONObject responseData;

    public void parseResponse(JSONObject json) {
        if (json != null) {
            responseId = json.optString(RESPONSE_ID_NAME);
            response = json.optJSONObject(RESPONSE_NAME);
            if (response != null) {
                responseData = response.optJSONObject(RESPONSE_MODEL_NAME);
            }
        }
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(RESPONSE_ID_NAME, responseId);
            if (responseData != null) {
                response.put(RESPONSE_MODEL_NAME, responseData);
            }
            jsonObject.put(RESPONSE_NAME, response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
