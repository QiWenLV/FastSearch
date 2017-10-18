package com.zqw.fastsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by 启文 on 2017/10/17.
 */
public class TabFragment extends Fragment implements MainActivity.OnClickNetListener{

    private MainActivity a;
    private Context context;
    private WebView webView;

    //https://www.baidu.com/s?wd=%E5%A4%A9%E6%89%8D%E6%9E%AA%E6%89%8B
    private WebSettings webSttings;
    private ProgressBar pd_loading;

    private int i;



    public TabFragment(Context context, int i){
        this.context = context;
        a = (MainActivity) context;
        this.i = i;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(context, R.layout.tab_fragment, null);
        webView = view.findViewById(R.id.web);
        pd_loading = view.findViewById(R.id.pd_loading);


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
                pd_loading.setVisibility(View.GONE);        //隐藏进度条
            }
        });

        webSttings.setBuiltInZoomControls(true);    //设置内置的缩放控件。
        webSttings.setUseWideViewPort(true);
        webSttings.setLoadWithOverviewMode(true);


        webView.setVisibility(View.GONE);
        a.setOnClickNetListener(this);



        return view;

    }



    @Override
    public void getWebNet(String key, String searchKey) {
        String url = null;
        Log.i("TAG", "key = "+ key + "      searchKey = "+ searchKey);
        switch (key){
            case "百度":
                url = Constant.BAIDU + searchKey;
                break;
            case "知乎":
                url = Constant.ZHIHU + searchKey;
                break;
            case "淘宝":
                url = Constant.TAOBAO + searchKey;
                break;
            default:
                url = Constant.BAIDU + searchKey;
                Log.i("TAG", "title:"+url);
                break;
        }
        Log.i("TAG", "url:"+url);

        webView.loadUrl(url);
        webView.setVisibility(View.VISIBLE);
    }

    public void getWebViewNet(String key, String searchKey) {
        String url = null;
        Log.i("TAG", "key = "+ key + "      searchKey = "+ searchKey);
        switch (key){
            case "百度":
                url = Constant.BAIDU + searchKey;
                break;
            case "知乎":
                url = Constant.ZHIHU + searchKey;
                break;
            case "淘宝":
                url = Constant.TAOBAO + searchKey;
                break;
            default:
                url = Constant.BAIDU + searchKey;
                Log.i("TAG", "title:"+url);
                break;
        }
        Log.i("TAG", "url:"+url);

        webView.loadUrl(url);
        webView.setVisibility(View.VISIBLE);
    }
}
