package com.zqw.fastsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 启文 on 2017/10/18.
 */
public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSttings;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_fragment);

        webView = findViewById(R.id.web);

        webSttings = webView.getSettings();
        //设置支持javaScript
        webSttings.setJavaScriptEnabled(true);
//        //设置双击变大变小
//        webSttings.setUseWideViewPort(true);
//        //设置缩放按钮
//        webSttings.setBuiltInZoomControls(true);

        //不让从当前网页跳转到系统的浏览器中
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webSttings.setBuiltInZoomControls(true);    //设置内置的缩放控件。
        webSttings.setUseWideViewPort(true);
        webSttings.setLoadWithOverviewMode(true);

        webView.loadUrl("https://www.baidu.com");
        webView.setVisibility(View.VISIBLE);

    }
}
