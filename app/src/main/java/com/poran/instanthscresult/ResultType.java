package com.poran.instanthscresult;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ResultType extends MainActivity implements AdapterView.OnItemSelectedListener {

    private Spinner exam,year,board;
    private EditText rollNo;
    private String []examList;
    private String []yearList;
    private String [] boardList;
    private SendMessageForInter inter;
    private String message;
    private AdView adView;
    private String number="16222";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    AdsView adsView;

    private InterstitialAd interstitialAd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.activity_result_type,null,false);
        mdrawer.addView(view,0);
        initview();



        //adsView.makeAdView();

        examList=getResources().getStringArray(R.array.exam_array);
        yearList=getResources().getStringArray(R.array.year_array);
        boardList=getResources().getStringArray(R.array.board_array);
        exam.setOnItemSelectedListener(this);
        year.setOnItemSelectedListener(this);
        board.setOnItemSelectedListener(this);
        inter=new SendMessageForInter();

        setContantInView(exam,examList);
        setContantInView(year,yearList);
        setContantInView(board,boardList);





    }

    public void setContantInView(Spinner sp,String[]iteamArray){
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,iteamArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }
    public void initview(){
        exam= (Spinner) findViewById(R.id.exam_spinner);
        year= (Spinner) findViewById(R.id.year_spinner);
        board= (Spinner) findViewById(R.id.board_spinner);
        rollNo= (EditText) findViewById(R.id.roll_edit);
        adView= (AdView) findViewById(R.id.bannerResultType);
    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
      Spinner spinner= (Spinner) parent;
        if(spinner.getId()==R.id.exam_spinner){
          inter.setExamination(examList[position]);
    }
        else if(spinner.getId()==R.id.year_spinner){
            inter.setYear(yearList[position]);
        }
        else if(spinner.getId()==R.id.board_spinner){
            setBoard(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void setBoard(int index){
        index++;
        if(index==2){
            inter.setBoard("DHA");

        }
        else if (index == 3) {
            inter.setBoard("BAR");
        }
        else if(index==4){
            inter.setBoard("CHI");

        }
        else if(index==5){
            inter.setBoard("COM");

        }
        else if(index==6){
            inter.setBoard("DIN");

        }
        else if(index==7){
            inter.setBoard("JES");

        }
        else if(index==8){
            inter.setBoard("RAJ");

        }
        else if(index==9){
            inter.setBoard("SYL");

        }
        else if(index==10){
            inter.setBoard("MAD");

        }


    }


//    public void setRollNo(){
//        inter.setRoll(rollNo.getText().toString());
//
//    }

    public void send(View view){

        String rollno=rollNo.getText().toString();
        inter.setRoll(rollno);
        message=inter.sendMessage();
        if(TextUtils.isEmpty(rollno)){
            Toast.makeText(this,"Please Enter your Roll No",Toast.LENGTH_SHORT).show();

        }
        else if(exam.getSelectedItemId()==0||board.getSelectedItemId()==0||year.getSelectedItemId()==0){
            Toast.makeText(this,"Please Select correctly",Toast.LENGTH_SHORT).show();
        }
        else {


            if (view.getId() == R.id.send_message) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.SEND_SMS)) {



                    } else {



                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.SEND_SMS},
                                MY_PERMISSIONS_REQUEST_SEND_SMS);

                    }
                } else {

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, message, null, null);
                    Toast.makeText(this, "SMS Sent " , Toast.LENGTH_SHORT).show();
                    rollNo.setText(null);
                    rollNo.setHint("Enter Roll Number");
                    exam.setSelection(0);
                    year.setSelection(0);
                    board.setSelection(0);
                }


            }
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_SEND_SMS:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SmsManager smsManager=SmsManager.getDefault();
                    smsManager.sendTextMessage(number,null,message,null,null);
                    Toast.makeText(this,"SMS Sent .",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Failed SMS,Try Again",Toast.LENGTH_SHORT).show();
                }
            }

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



}
