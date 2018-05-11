package com.poran.instanthscresult;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBack extends MainActivity {
    private EditText nameView,messageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater= (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        View container=layoutInflater.inflate(R.layout.activity_feed_back,null,false);
        mdrawer.addView(container,0);

        initView();
    }

    public void initView(){
        nameView= (EditText) findViewById(R.id.nameFd);
        messageView= (EditText) findViewById(R.id.mFeedBack);

    }

    public void sendFeedback(View view){
        String name=nameView.getText().toString();
        String message=messageView.getText().toString();

        try{

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/email");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"poran.cse@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback From App");
            intent.putExtra(Intent.EXTRA_TEXT,"Name :"+name+"\n"+"Message :"+message);
            startActivity(Intent.createChooser(intent,"Feedback With"));


        }catch (Resources.NotFoundException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
