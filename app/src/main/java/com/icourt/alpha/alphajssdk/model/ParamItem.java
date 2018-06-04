package com.icourt.alpha.alphajssdk.model;

import android.text.TextUtils;
import com.icourt.alpha.alphajssdk.annotation.Param;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/1.
 */
public class ParamItem extends BaseParamItem {

    public boolean needConvert;

    public ParamItem(String paramKey, Class paramClass, boolean needConvert) {
        super(paramClass, paramKey);
        this.needConvert = needConvert;
    }

    @Override
    public Object convertJson2ParamValue(RequestResponseBuilder requestResponseBuilder) {
        if (requestResponseBuilder == null || requestResponseBuilder.getValues() == null) {
            return null;
        }
        JSONObject jsonObject = requestResponseBuilder.getValues();
        if (jsonObject != null) {
            if (needConvert) {
                try {
                    JSONObject value = !TextUtils.isEmpty(paramKey) ? (JSONObject) jsonObject.opt(paramKey) : jsonObject;
                    if(value == null){
                        return null;
                    }
                    Object instance = paramType.newInstance();
                    Field[] fields = paramType.getDeclaredFields();
                    for (Field field : fields
                            ) {
                        Param p = field.getAnnotation(Param.class);
                        if (p != null) {
                            /*可以访问不可以访问的变量*/
                            field.setAccessible(true);
                            field.set(instance, value.opt(p.value()));
                        }
                    }
                    return instance;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                return jsonObject.opt(paramKey);
            }
        }
        return null;
    }

    @Override
    public void convertParamValue2Json(RequestResponseBuilder requestResponseBuilder, Object obj) {

        if (requestResponseBuilder == null || obj == null) {
            return;
        }
        /*需要进行转换*/
        if (needConvert) {
            JSONObject objectParamJson = null;
            if (!TextUtils.isEmpty(paramKey)) {
                objectParamJson = new JSONObject();
            }

            Class cl = obj.getClass();
            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields
                    ) {
                Param p = field.getAnnotation(Param.class);
                if (p != null) {
                    /*可以访问不可以访问的变量*/
                    field.setAccessible(true);
                    Object inst = null;
                    try {
                        inst = field.get(obj);
                        if (inst != null) {
                            if (objectParamJson != null) {

                                objectParamJson.put(p.value(), inst);
                            } else {
                                requestResponseBuilder.putValue(p.value(), inst);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (objectParamJson != null) {
                requestResponseBuilder.putValue(paramKey, objectParamJson);
            }
        } else {
            requestResponseBuilder.putValue(paramKey, obj);

        }
    }
}
