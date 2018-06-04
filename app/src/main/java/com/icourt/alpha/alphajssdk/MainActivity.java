package com.icourt.alpha.alphajssdk;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.icourt.alpha.alphajssdk.fragment.WebViewFragment;

/**
 * @author angleterry  email:shibukun@icourt.cc
 * @description
 * @company Beijing icourt
 * @date 2018/6/1.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView() {
        String url = "file:///android_asset/bridge_demo.html";
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.webview_layout);
        if (fragment == null || !(fragment instanceof WebViewFragment)) {
            fragment = (WebViewFragment.createWebViewFragment(url));
        }
        fm.beginTransaction().replace(R.id.webview_layout, fragment).commit();
    }
}
