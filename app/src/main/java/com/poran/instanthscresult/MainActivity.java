package com.poran.instanthscresult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends Activity {
    private AdView mAdView;
    private InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);

      mAdView = (AdView) findViewById(R.id.adView);

        getAds();


    }
public void getResult(View view){

    adLoad();
    Intent intent=new Intent(this,ResultType.class);
    startActivity(intent);


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

public void getAds(){

    AdRequest adRequest = new AdRequest.Builder()
            .setRequestAgent("android_studio:ad_template")
            .addTestDevice("TEST_DEVICE_ID")
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build();

    mAdView.loadAd(adRequest);

}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    //.setTitle("Do You Want TO Exit")
                    .setMessage("Do You Want TO Exit?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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

                            //Stop the activity
                            MainActivity.this.finish();
                        }

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
