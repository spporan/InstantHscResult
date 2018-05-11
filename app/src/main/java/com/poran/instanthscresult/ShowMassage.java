package com.poran.instanthscresult;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ShowMassage extends MainActivity {
    private ListView listMessage;
    private DataBaseHelper dataBaseHelper;

    private ArrayList<String>remindMessage=new ArrayList<>();
    private ArrayList<String>remindTime=new ArrayList<>();
    PendingIntent pendingIntent;

    private AdView adView;
    AdsView adsView;

    AlarmManager alarmManager;
    Intent intent;
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);
    protected static int requestCode=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View container=inflater.inflate(R.layout.activity_show_massage,null,false);
        mdrawer.addView(container,0);

        adView= (AdView) findViewById(R.id.bannershow);
        adsView=new AdsView(this,adView);
       // adsView.makeAdView();

        listMessage= (ListView) findViewById(R.id.messagelist);
        dataBaseHelper=new DataBaseHelper(this);
       // readDataBase();

        showReminderList();




    }

    public void showReminderList(){
        setData();

        CustomList adapter=new CustomList(getApplicationContext(),remindMessage,remindTime);
        listMessage.setAdapter(adapter);

    }

    public void setData(){
        Calendar calendar=Calendar.getInstance();
        final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

        Cursor cursor=dataBaseHelper.readData();


        cursor.moveToFirst();


        while (cursor.moveToNext()){

            calendar.set(Calendar.HOUR_OF_DAY,cursor.getInt(2));
            calendar.set(Calendar.MINUTE,cursor.getInt(3));
            remindMessage.add(cursor.getString(1));
            remindTime.add(timeFormat.format(calendar.getTime()));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
