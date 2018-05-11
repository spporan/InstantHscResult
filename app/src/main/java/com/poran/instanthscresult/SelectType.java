package com.poran.instanthscresult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SelectType extends MainActivity {


    private String url="http://eboardresults.com/app/stud/";
    private String pscUrl="http://180.211.137.51:5839/";

    private AdView adView;

    private int savedCheckedValue;
    public Intent intent;
    AdsView adsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.activity_select_type,null,false);
        mdrawer.addView(view,0);

        Bundle bundle=getIntent().getExtras();

        adView= (AdView) findViewById(R.id.bannerType);
        adsView=new AdsView(this,adView);
       // adsView.makeAdView();


        savedCheckedValue=bundle.getInt("Check");

    }

    public void checkedType(View view){

        if(view.getId()==R.id.checkedonline){
           resultForOnline();


        }
        if(view.getId()==R.id.checkedoffline){
           resultForOffline();
        }

    }

    public void resultForOnline(){

        if(checkedValue()==4){


            intent=new Intent(this,Main1Activity.class);
            intent.putExtra("URL",pscUrl);
            startActivity(intent);


        }
        else{
            intent=new Intent(this,Main1Activity.class);
            intent.putExtra("URL",url);
            startActivity(intent);

        }
    }



    public void resultForOffline(){

        if(checkedValue()==4){
            intent=new Intent(this,PrimaryResult.class);
        }
        else {
            intent=new Intent(this,ResultType.class);
        }
        startActivity(intent);
    }



    public int checkedValue(){
        int flagValue=0;
        if(savedCheckedValue==1){
           flagValue=1;

        }
        else if(savedCheckedValue==2){
            flagValue=2;
        }
        else if(savedCheckedValue==3){
            flagValue=3;
        }
        else if(savedCheckedValue==4){
            flagValue=4;
        }
        return flagValue;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
