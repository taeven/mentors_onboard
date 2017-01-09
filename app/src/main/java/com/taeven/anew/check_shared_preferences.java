package com.taeven.anew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class check_shared_preferences extends AppCompatActivity {

    private shared_preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_shared_preferences);
         preference = new shared_preference(this);
        initiate_intent();


    }


    private void initiate_intent()
    {
        if(preference.checkExist())
        {
            Intent intent =new Intent("com.taeven.anew.after_login");
            startActivity(intent);
        }
        else
        {
            Intent intent =new Intent("com.taeven.anew.login");
            startActivity(intent);
        }
    }
}
