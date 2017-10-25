package com.poran.instanthscresult;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Main1Activity extends Activity {
    public  String url = "http://eboardresults.com/app/stud/";;
    private WebView wv1 ;
    private InterstitialAd interstitialAd=null;
    SwipeRefreshLayout mSwipeRefreshLayout;

    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        wv1=(WebView)findViewById(R.id.webview);

        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ads));
        AdRequest adRequest=new AdRequest.Builder().build();

        interstitialAd.loadAd(adRequest);

        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        wv1.setWebViewClient(new Main1Activity.MyBrowser());


        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.loadUrl(url);


        wv1.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });



        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {

                    @Override
                    public void onRefresh() {

                        wv1.reload();

                    }
                }
        );


    }
//work for backpressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            adLoad();


            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv1.canGoBack()) {
                        wv1.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }

        return super.onKeyDown(keyCode, event);
    }
    //Load Ads
public void adLoad(){

    if (interstitialAd.isLoaded()) {
        interstitialAd.show();
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }
        });
    }
}

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


}
