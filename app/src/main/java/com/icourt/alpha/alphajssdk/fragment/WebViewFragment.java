package com.icourt.alpha.alphajssdk.fragment;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.TextView;
import com.icourt.alpha.alphajssdk.*;
import com.icourt.alpha.alphajssdk.annotation.JavaCallback4JS;
import com.icourt.alpha.alphajssdk.annotation.Param;
import com.icourt.alpha.alphajssdk.annotation.ParamResponseStatus;
import com.icourt.alpha.alphajssdk.interfaces.WebviewLoadUrlListener;

public class WebViewFragment extends Fragment implements WebviewLoadUrlListener {

    public static final String WEBVIEW_URL = "webview_url";

    private WebView webView;
    private String url;
    private TextView resultView;

    private AlphaJavaJsBridge alphaJavaJsBridge;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        parseArguments();
        initView();
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    public static Bundle createBundle(String url) {
        Bundle args = new Bundle();
        args.putString(WEBVIEW_URL, url);

        return args;
    }

    public static WebViewFragment createWebViewFragment(Bundle args) {
        WebViewFragment instance = new WebViewFragment();
        instance.setArguments(args);
        return instance;
    }

    public static WebViewFragment createWebViewFragment(String url) {
        return createWebViewFragment(createBundle(url));
    }

    protected void parseArguments() {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        url = args.getString(WEBVIEW_URL);

    }

    public void initData() {
        JavaInterfaces4JS javaInterfaces4JS = new JavaInterfaces4JS(this);
        alphaJavaJsBridge = new AlphaJavaJsBridge.Builder().addJavaInterface4JS(javaInterfaces4JS)
                .setLoadListener(this)
                .setJSMethodName4Java("_JSNativeBridge._handleMessageFromNative")
                .setProtocol("icourt", "alpha").create();
        webView.setWebChromeClient(new AlphaJavaJSWebChromeClient(alphaJavaJsBridge));
    }

    private void showLoading() {
        resultView.setText("正在调用js的接口......");
    }

    public void setResult(String result) {
        if (result != null) {
            resultView.setText(result);
        }
    }

    public void initView() {
        initWebView();

        resultView = (TextView) getView().findViewById(R.id.result);
        getView().findViewById(R.id.invoke_js_exam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                IInvokeJS invokeJS = alphaJavaJsBridge.createInvokJSCommand(IInvokeJS.class);
                invokeJS.exam("你好啊js", 7, new IJavaCallback2JS() {

                    @JavaCallback4JS
                    public void test(@ParamResponseStatus("msg") String statusMsg, @Param("msg") String msg) {
                        resultView.setText(" 状态信息=" + statusMsg + "  msg=" + msg);
                    }

                });
            }
        });

        getView().findViewById(R.id.invoke_js_exam1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                IInvokeJS invokeJS = alphaJavaJsBridge.createInvokJSCommand(IInvokeJS.class);
                IInvokeJS.City city = new IInvokeJS.City();
                city.cityId = 10;
                city.cityName = "长治";
                city.cityProvince = "山西";
                invokeJS.exam1(city, new IJavaCallback2JS() {

                    @JavaCallback4JS
                    public void test(@Param(needConvert = true) IInvokeJS.City city1) {
                        resultView.setText("js返回信息： cityName=" + city1.cityName + "  cityProvince=" + city1.cityProvince);
                    }

                });
            }
        });
        getView().findViewById(R.id.invoke_js_exam2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                IInvokeJS invokeJS = alphaJavaJsBridge.createInvokJSCommand(IInvokeJS.class);
                IInvokeJS.City city = new IInvokeJS.City();
                city.cityId = 10;
                city.cityName = "长治";
                city.cityProvince = "山西";
                invokeJS.exam2(city, "中国", new IJavaCallback2JS() {

                    @JavaCallback4JS
                    public void test(@Param(value = "city", needConvert = true) IInvokeJS.City city1) {
                        resultView.setText("js返回信息： cityName=" + city1.cityName + "  cityProvince=" + city1.cityProvince);
                    }

                });
            }
        });
        getView().findViewById(R.id.invoke_js_exam3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                IInvokeJS invokeJS = alphaJavaJsBridge.createInvokJSCommand(IInvokeJS.class);
                IInvokeJS.City city = new IInvokeJS.City();
                city.cityId = 10;
                city.cityName = "长治";
                city.cityProvince = "山西";
                invokeJS.exam3(city, "中国", new IJavaCallback2JS() {

                    @JavaCallback4JS
                    public void test(@Param(needConvert = true) IInvokeJS.City city1) {
                        resultView.setText("js返回信息： cityName=" + city1.cityName + "  cityProvince=" + city1.cityProvince);
                    }

                });
            }
        });

        getView().findViewById(R.id.invoke_js_exam4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                IInvokeJS invokeJS = alphaJavaJsBridge.createInvokJSCommand(IInvokeJS.class);

                invokeJS.exam4(new IJavaCallback2JS() {

                    @JavaCallback4JS
                    public void test(@ParamResponseStatus("success") String status, @ParamResponseStatus("msg") String statusMsg) {
                        resultView.setText("js返回信息： status=" + status + "  statusMsg=" + statusMsg);
                    }

                });
            }
        });

        getView().findViewById(R.id.invoke_js_exam4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IInvokeJS invokeJS = alphaJavaJsBridge.createInvokJSCommand(IInvokeJS.class);
                IInvokeJS.City city = new IInvokeJS.City();
                city.cityId = 10;
                city.cityName = "长治";
                city.cityProvince = "山西";
                invokeJS.exam5(city, "中国");
            }
        });

    }

    private void initWebView() {
        webView = (WebView) getView().findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webViewCallBack.shouldOverrideUrlLoading(view, url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                webViewCallBack.onPageStarted(view, url, favicon);

            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        });


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setBuiltInZoomControls(true);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);

        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }
}
