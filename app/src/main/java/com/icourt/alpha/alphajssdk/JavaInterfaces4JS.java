package com.icourt.alpha.alphajssdk;

import com.icourt.alpha.alphajssdk.annotation.JavaInterface4JS;
import com.icourt.alpha.alphajssdk.annotation.Param;
import com.icourt.alpha.alphajssdk.annotation.ParamCallback;
import com.icourt.alpha.alphajssdk.annotation.ParamResponseStatus;
import com.icourt.alpha.alphajssdk.fragment.WebViewFragment;

/**
 * java提供给js的接口
 * Created by niuxiaowei on 16/7/22.
 */
public class JavaInterfaces4JS {


    private WebViewFragment mWebViewFragment;

    public JavaInterfaces4JS(WebViewFragment webViewFragment) {
        mWebViewFragment = webViewFragment;
    }


    /**
     * 必须有无参构造函数
     */
    public static class Person {
        @Param("name")
        String name;
        @Param("age")
        public int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    /**
     * 发送响应状态的接口
     */
    public interface IResponseStatusCallback {
        void callbackResponse(@ParamResponseStatus("status") int status, @ParamResponseStatus("msg") String msg);
    }

    public interface ITestJSCallback extends IResponseStatusCallback {
        void callback(@ParamResponseStatus("status") int status, @ParamResponseStatus("msg") String msg, @Param("content") String content);
    }

    public interface ITest1JSCallback extends IResponseStatusCallback {
        void callback(@ParamResponseStatus("status") int status, @ParamResponseStatus("msg") String msg, @Param(needConvert = true) Person person);
    }

    @JavaInterface4JS("test")
    public void test(@Param("msg") String msg, @ParamCallback ITestJSCallback jsCallback) {
        mWebViewFragment.setResult("js传递数据: " + msg);
        jsCallback.callbackResponse(-1, "错误");
    }


    @JavaInterface4JS("test1")
    public void test(@Param(needConvert = true) Person personInfo, @ParamCallback ITest1JSCallback jsCallback) {

        if (personInfo != null) {
            mWebViewFragment.setResult("native的test1接口被调用，js传递数据: " + "name=" + personInfo.name + " age=" + personInfo.age);

        }
        jsCallback.callback(1, "ok", new Person("niuxiaowei", 30));
    }


    @JavaInterface4JS("test2")
    public void test2(@Param(value = "person", needConvert = true) Person personInfo, @ParamCallback ITest1JSCallback jsCallback) {

        if (personInfo != null) {
            mWebViewFragment.setResult("native的test2接口被调用，js传递数据: " + "name=" + personInfo.name + " age=" + personInfo.age);

        }
        jsCallback.callback(1, "ok", new Person("niuxiaowei", 30));
    }

    @JavaInterface4JS("test3")
    public void test3(@Param("jiguan") String jiguan, @Param(value = "person", needConvert = true) Person personInfo, @ParamCallback ITest1JSCallback jsCallback) {

        if (personInfo != null) {
            mWebViewFragment.setResult("native的test3接口被调用，js传递的数据: " + "jiguan=" + jiguan + " name=" + personInfo.name + " age=" + personInfo.age);

        }
        jsCallback.callback(1, "ok", new Person("niuxiaowei", 30));
    }

    @JavaInterface4JS("test4")
    public void test3(@ParamCallback IResponseStatusCallback jsCallback) {

        mWebViewFragment.setResult("native的test4无参接口被调用");

        jsCallback.callbackResponse(1, "ok");
    }

    @JavaInterface4JS("test5")
    public void test4() {

        mWebViewFragment.setResult("native的test5无参接口被调用");

//        jsCallback.callbackResponse(1, "ok");
    }
}
