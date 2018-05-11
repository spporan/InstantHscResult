package com.poran.instanthscresult;

import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class PrimaryResult extends MainActivity implements AdapterView.OnItemSelectedListener {
    private EditText rollNumberP,code;
    private static WebView webViewP;
    private Spinner pExam;
    private String number="16222";
    private AdView adView;
    private SendMessageForInter primary;
    private String pExamList[]={"Choose One","DPE","EBT"};
    private static String url="http://www.bangladeshpost.gov.bd/PostCode1.asp";

    AdsView adsView;
    InterstitialAd interstitialAd=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_primary_result, null, false);
        mdrawer.addView(contentView,0);
        adView= (AdView) findViewById(R.id.bannerPrimary);
        adsView=new AdsView(this,adView);
       // adsView.makeAdView();


        webViewP= (WebView) findViewById(R.id.post);
        pExam= (Spinner) findViewById(R.id.examP_spinner);
        code= (EditText) findViewById(R.id.set_code);
        rollNumberP= (EditText) findViewById(R.id.set_Roll);
        primary=new SendMessageForInter();


        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.testInterstitial_ads));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        adsView=new AdsView(this,adView);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,pExamList);
        pExam.setAdapter(adapter);

        pExam.setOnItemSelectedListener(this);



    }
    public void getCode(View view){
        if(view.getId()==R.id.get_code){
        viewPage(webViewP);
        }
        else if(view.getId()==R.id.sendP_message){


            String roll=rollNumberP.getText().toString();
            String tCode=code.getText().toString();

            primary.setRoll(roll);
            primary.setThanaCode(tCode);

            if(TextUtils.isEmpty(roll)||TextUtils.isEmpty(tCode)){
                Toast.makeText(getApplicationContext(),"Please,Input properly",Toast.LENGTH_SHORT).show();

            }
            else if(pExam.getSelectedItemId()==0){

                Toast.makeText(getApplicationContext(),"Please,Select properly",Toast.LENGTH_SHORT).show();
            }
            else {

                String message=primary.sendPMessage();
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(number,null,message,null,null);
                Toast.makeText(this,"Sent SMS",Toast.LENGTH_SHORT).show();

                rollNumberP.setText("");
                rollNumberP.setHint("Enter Roll No");
                pExam.setSelection(0);
                code.setText("");
                code.setHint("Enter Upozila Code");

            }






        }
    }
    public static void viewPage(WebView webView){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MywebView());
        webView.loadUrl(url);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        primary.setExamination(pExamList[position]);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private static class MywebView extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
