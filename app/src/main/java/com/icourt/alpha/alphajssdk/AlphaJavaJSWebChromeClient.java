package com.icourt.alpha.alphajssdk;

import android.content.Context;
import android.webkit.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/1.
 */

public class AlphaJavaJSWebChromeClient extends WebChromeClient {

    public boolean mIsInjectedJS;

    private AlphaJavaJsBridge mSimpleJavaJsBridge;

    public AlphaJavaJSWebChromeClient(AlphaJavaJsBridge simpleJavaJsBridge) {
        mSimpleJavaJsBridge = simpleJavaJsBridge;
    }

    private static void webViewLoadLocalJs(WebView view, String path) {
        String jsContent = assetFile2Str(view.getContext(), path);
        view.loadUrl("javascript:" + jsContent);
    }

    private static String assetFile2Str(Context c, String urlStr) {
        InputStream in = null;
        try {
            in = c.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                    line = line.replaceAll("\\t", "   ");
                    if (!line.matches("^\\s*\\/\\/.*")) {
                        sb.append(line);
                    }
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (mSimpleJavaJsBridge.parseJsonFromJs(message)) {
            /*必须得有这行代码，否则会阻塞当前h5页面*/
            result.cancel();
            return true;
        }

        return super.onJsPrompt(view, url, message, defaultValue, result);
    }
}
