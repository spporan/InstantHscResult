package com.poran.instanthscresult;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AutoMessageSending extends MainActivity implements AdapterView.OnItemSelectedListener{
    private Spinner examRemind,boardRemind,yearRemind;
    private EditText rollRemind,postCodeRemind;
    private static WebView webView;
    private AdView adView;
    private Button setTime;
    private  String[]examRemindArray;
    private  String[]boardRemindArray;
    private  String[]yearRemindArray;
    private RelativeLayout layoutForHigh,layoutForPsc;
    private SendMessageForInter inter;
    private boolean check=false;
    int hour,minute;
    boolean timeset=true;


    protected static int requestCode=0;
    DataBaseHelper dataBaseHelper;
    PendingIntent pendingIntent;
    Handler handler;
    Runnable runnable;
    AlarmManager alarmManager;
    Intent intent;
    private static int timeHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    private static int timeMinute = Calendar.getInstance().get(Calendar.MINUTE);


    AdsView adsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_auto_message_sending, null, false);
        mdrawer.addView(contentView,0);

        adsView=new AdsView(this,adView);
        //adsView.makeAdView();


       examRemindArray=getResources().getStringArray(R.array.exam_array_remind);
        boardRemindArray=getResources().getStringArray(R.array.board_array_remind);
        yearRemindArray=getResources().getStringArray(R.array.year_array);


        inter=new SendMessageForInter();
        dataBaseHelper=new DataBaseHelper(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_appbar);

        mtoolbar.findViewById(R.id.setChecked).setOnClickListener(new View.OnClickListener() {

            //set message as Reminder
            @Override
            public void onClick(View v) {


                  addDataToDataBase();


                  setToast(inter.getMessage());
                requestCode++;
                setPending();

                  startActivity(new Intent(getApplicationContext(), ShowMassage.class));






            }
        });




        initView();
        setResources(examRemind,examRemindArray);
        setResources(boardRemind,boardRemindArray);
        setResources(yearRemind,yearRemindArray);


        examRemind.setOnItemSelectedListener(this);
        boardRemind.setOnItemSelectedListener(this);
        yearRemind.setOnItemSelectedListener(this);








    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initView(){
        examRemind= (Spinner) findViewById(R.id.exam_spinner_remind);
        boardRemind= (Spinner) findViewById(R.id.board_spinner_remind);
        yearRemind= (Spinner) findViewById(R.id.year_spinner_remind);
        rollRemind= (EditText) findViewById(R.id.roll_edit_remind);
        postCodeRemind= (EditText) findViewById(R.id.set_code_remind);
        layoutForHigh= (RelativeLayout) findViewById(R.id.secondary_result);
        setTime= (Button) findViewById(R.id.setTime);
        layoutForPsc= (RelativeLayout) findViewById(R.id.primary_Result);
        webView= (WebView) findViewById(R.id.post_remind);
        adView= (AdView) findViewById(R.id.bannerAuto);

    }

    public void setTimePicker(){
        final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        final Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AutoMessageSending.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,selectedHour);
                calendar.set(Calendar.MINUTE,selectedMinute);

                if(selectedMinute<10){
                    setTime.setText(timeFormat.format(calendar.getTime()));

                    //setTime.setText( selectedHour + ":"+"0" + selectedMinute);
                }else{
                   // setTime.setText( selectedHour + ":" + selectedMinute);
                    setTime.setText(timeFormat.format(calendar.getTime()));
                }


                inter.setHour(selectedHour);
                inter.setMinute(selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
       timeset=false;

    }

    public void setResources(Spinner sp,String [] iteamArray){
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,iteamArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }

    public void setTime(View view){
        setTimePicker();

    }



    public void setVisibility(int position){
        if(position==7 ||position==8){
            layoutForPsc.setVisibility(View.VISIBLE);
            layoutForHigh.setVisibility(View.GONE);
            check=true;

        }
        else if(position==0){

            layoutForPsc.setVisibility(View.GONE);
            layoutForHigh.setVisibility(View.GONE);
        }
        else {
            check=false;
            layoutForHigh.setVisibility(View.VISIBLE);
            layoutForPsc.setVisibility(View.GONE);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner= (Spinner) parent;
        if(spinner.getId()==R.id.exam_spinner_remind){

            setVisibility(position);
            inter.setExamination(examRemindArray[position]);

        }
        else if(spinner.getId()==R.id.year_spinner_remind){
            inter.setYear(yearRemindArray[position]);
        }
        else if(spinner.getId()==R.id.board_spinner_remind){
            setBoard(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setMessage(){
        inter.setRoll(rollRemind.getText().toString());
        if(check){


                inter.setThanaCode(postCodeRemind.getText().toString());
                String msg=inter.sendPMessage();
                inter.setMessage(msg);








        }else{

                String msg=inter.sendMessage();
                inter.setMessage(msg);


            }






    }


    public void getCodeRemind(View view){

        PrimaryResult.viewPage(webView);


    }

    public void setToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show();
    }

    private void setBoard(int index) {
        index++;
        if (index == 2) {
            inter.setBoard("DHA");

        } else if (index == 3) {
            inter.setBoard("BAR");
        } else if (index == 4) {
            inter.setBoard("CHI");

        } else if (index == 5) {
            inter.setBoard("COM");

        } else if (index == 6) {
            inter.setBoard("DIN");

        } else if (index == 7) {
            inter.setBoard("JES");

        } else if (index == 8) {
            inter.setBoard("RAJ");

        } else if (index == 9) {
            inter.setBoard("SYL");

        } else if (index == 10) {
            inter.setBoard("MAD");

        }
    }

    public void addDataToDataBase(){


        setMessage();
        if(dataBaseHelper.addData(inter)){
            //setToast("Successfully added data");

        }else{
            setToast("Failed to add");
        }




    }






    public void setPending(){
        final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        Calendar calendar=Calendar.getInstance();
        Cursor cursor=dataBaseHelper.readData();



        Intent intent=new Intent(getApplicationContext(),MyReceiver.class);

        while(cursor.moveToNext()){
            intent.putExtra("ID",cursor.getString(0));
            intent.putExtra("Message",cursor.getString(1));
            calendar.set(Calendar.HOUR_OF_DAY,cursor.getInt(2));
            calendar.set(Calendar.MINUTE,cursor.getInt(3));
        }
        calendar.set(Calendar.SECOND,0);


        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,requestCode,intent,0);
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        Toast.makeText(getApplicationContext(),"Message will send "+timeFormat.format(calendar.getTime()),Toast.LENGTH_SHORT).show();


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


    private void setBootReceiverEnabled(int componentEnabledState) {

        ComponentName componentName = new ComponentName(this, BootReceiver.class);
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,
                componentEnabledState,
                PackageManager.DONT_KILL_APP);
    }









}
