package com.poran.instanthscresult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
public class Examinations extends MainActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.examinations, null, false);
        mdrawer.addView(contentView,0);

        adView= (AdView) findViewById(R.id.bannerExam);
       //AdsView adsView=new AdsView(this,adView);
        //adsView.makeAdView();


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
    public void getExam(View view){

        Intent intent;
        if(view.getId()==R.id.hsc){
            intent=new Intent(this,SelectType.class);
            intent.putExtra("Check",1);
            startActivity(intent);
        }
        if(view.getId()==R.id.ssc){
            intent=new Intent(this,SelectType.class);
            intent.putExtra("Check",2);
            startActivity(intent);
        }
        if(view.getId()==R.id.jsc){
            intent=new Intent(this,SelectType.class);
            intent.putExtra("Check",3);
            startActivity(intent);
        }
        if(view.getId()==R.id.psc){
            intent=new Intent(this,SelectType.class);
            intent.putExtra("Check",4);
            startActivity(intent);
        }
    }
}
