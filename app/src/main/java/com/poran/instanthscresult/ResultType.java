package com.poran.instanthscresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ResultType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_type);
    }

    public void getResult(View view){

        switch (view.getId()){
            case R.id.offline:
                Intent offlineIntent=new Intent(this,ResultViaMessage.class);
                startActivity(offlineIntent);
                break;
            case R.id.online:
                Intent onlineIntent =new Intent(this,ResultviaOnline.class);
                startActivity(onlineIntent);
                break;
        }

    }
}
