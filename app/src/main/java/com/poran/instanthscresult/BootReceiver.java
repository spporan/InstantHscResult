package com.poran.instanthscresult;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.Calendar;

/**
 * Created by poran on 29-Dec-17.
 */

public class BootReceiver extends BroadcastReceiver {

int requestCode=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            setAlarm(context);
        }
    }

    public void setAlarm(Context context) {
        DataBaseHelper dataBaseHelper=new DataBaseHelper(context);
        Cursor cursor=dataBaseHelper.readData();

        Calendar calendar=Calendar.getInstance();
        Intent intent = new Intent(context, MyReceiver.class);
        while(cursor.moveToNext()){

            intent.putExtra("ID",cursor.getString(0));
            intent.putExtra("Message",cursor.getString(1));
            calendar.set(Calendar.HOUR_OF_DAY,cursor.getInt(2));
            calendar.set(Calendar.MINUTE,cursor.getInt(3));


        }



        calendar.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode , intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), calendar.getTimeInMillis(), pendingIntent);
    }
}
