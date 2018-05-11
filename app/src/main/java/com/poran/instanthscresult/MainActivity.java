package com.poran.instanthscresult;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    protected DrawerLayout mdrawer;
    private ActionBarDrawerToggle mtoggle;
    protected Toolbar mtoolbar;
    private NavigationView mNavView;
    private AdView mAdView;
    private InterstitialAd interstitialAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdrawer=(DrawerLayout) findViewById(R.id.drawer);
        mNavView=(NavigationView)findViewById(R.id.nav_id);
        mtoolbar=(Toolbar)findViewById(R.id.nav_tool);


        setSupportActionBar(mtoolbar);
        mtoggle=setToggle();
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();
        setUpNevigationContent(mNavView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);







        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial));
        AdRequest adRequest = new AdRequest.Builder().build();
       // interstitialAd.loadAd(adRequest);

    //  mAdView = (AdView) findViewById(R.id.adView);

        //getAds();


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.manu_item, menu);
        return true;
    }

    private void setUpNevigationContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.feedback:
               startActivity(new Intent(this,FeedBack.class));
                break;
            case R.id.share:
                shareApp();
                break;
            case R.id.remind:
                startActivity( new Intent(getApplicationContext(),AutoMessageSending.class));
                break;
            case R.id.show:
                startActivity(new Intent(getApplicationContext(),ShowMassage.class));
                break;
            case R.id.rateUs:
                rateOurApp();
                break;

            case R.id.exit:
                finish();

                moveTaskToBack(true);

                break;

            case R.id.home:
                startActivity(new Intent(this,Examinations.class));
                break;
        }

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mdrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setToggle(){
        return new ActionBarDrawerToggle(this,mdrawer,R.string.open,R.string.close);
    }

    public void shareApp(){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Get Quick Public Exam Result");
            String url = "\nLet me recommend you this application\n\n";
            url = url + "https://play.google.com/store/apps/details?id="+this.getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(intent, "Share one"));
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public void rateOurApp(){



        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
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

//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //Handle the back button
//        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            //Ask the user if they want to quit
//            new AlertDialog.Builder(this)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    //.setTitle("Do You Want TO Exit")
//                    .setMessage("Do You Want TO Exit?")
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            if (interstitialAd.isLoaded()) {
//                                interstitialAd.show();
//                                interstitialAd.setAdListener(new AdListener() {
//                                    @Override
//                                    public void onAdClosed() {
//                                        super.onAdClosed();
//                                        finish();
//                                    }
//                                });
//                            }
//
//                            //Stop the activity
//                            MainActivity.this.finish();
//                        }
//
//                    })
//                    .setNegativeButton("Cancel", null)
//                    .show();
//
//            return true;
//        }
//        else {
//            return super.onKeyDown(keyCode, event);
//        }
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mtoggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        mtoggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);
    }



}
