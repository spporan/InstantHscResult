package com.poran.instanthscresult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by poran on 27-Dec-17.
 */

public class MyReceiver extends BroadcastReceiver {
    private String number="16222";
    DataBaseHelper dataBaseHelper;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        String message=intent.getStringExtra("Message");
        String id=intent.getStringExtra("ID");
        sendMessage(number,message);
      dataBaseHelper=new DataBaseHelper(context);

        Toast.makeText(context,"Sending Message ...",Toast.LENGTH_LONG).show();

        if(dataBaseHelper.deleMessage(id)>0){



        }
        else{
            Toast.makeText(context,"Error occurred",Toast.LENGTH_LONG).show();
        }


    }

    public void sendMessage(String num,String message){
        try{
            SmsManager manager=SmsManager.getDefault();
            manager.sendTextMessage(num,null,message,null,null);
        }catch (Exception e){

            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }



    }
}
