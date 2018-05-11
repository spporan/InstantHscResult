package com.poran.instanthscresult;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Main1Activity extends MainActivity {

    private WebView webView;
    private String ur;
    ProgressDialog progressDialog;
    private AdView adView;
    private InterstitialAd interstitialAd = null;
    

    AdsView adsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.testInterstitial_ads));
        AdRequest adRequest = new AdRequest.Builder().build();
         interstitialAd.loadAd(adRequest);


        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main1, null, false);
        mdrawer.addView(contentView,0);

        adView= (AdView) findViewById(R.id.bannerInter);
        adsView=new AdsView(this,adView);
        adsView.makeAdView();

        progressDialog=ProgressDialog.show(this,"Loading","Please wait...",true);
        progressDialog.setCancelable(false);

        Bundle bundle=getIntent().getExtras();
        ur=bundle.getString("URL");

        webView= (WebView) findViewById(R.id.webview);
        viewSite();


    }

    public void viewSite(){
        webView.setWebViewClient(new MyWebview());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(ur);



    }

    private class MyWebview extends WebViewClient{


        @Override
        public void onPageFinished(WebView view, String url) {
            webView.loadUrl("javascript:(function() { " +
                    "document.getElementById('main_header2').style.display='none';})()");
            webView.loadUrl("javascript:(function() { " +
                    "document.getElementById('dev_info').style.display='none';})()");

            webView.loadUrl("javascript:(function() { " +
                    "document.getElementById('twitter-follow').style.display='none';})()");

            webView.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('panel-heading')[0].style.display='none'; })()");


            progressDialog.dismiss();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        if (adView!= null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeAdView();
        if (adView != null) {
            adView.resume();
        }
    }
    private void makeAdView() {
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
        adView.setAdListener(getAdListener(adRequest));
        adView.bringToFront();
    }

    private AdListener getAdListener(final AdRequest adRequest) {
        return new AdListener() {
            // hide ad block if none could be found
            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.loadAd(adRequest);
                switch (errorCode) {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        break;
                }
            }

            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }
        };


    }
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            if (interstitialAd.isLoaded()) {
                                interstitialAd.show();
                                interstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdClosed() {
                                        super.onAdClosed();
                                        finish();
                                    }
                                });
                            }
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }



}
