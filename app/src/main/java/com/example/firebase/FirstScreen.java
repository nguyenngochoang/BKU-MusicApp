package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.Activity.OfflineActivity;
import com.Activity.OnlineActivity;

import static com.example.firebase.MainActivity.PREF_NAME;

public class FirstScreen extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar

        setContentView(R.layout.activity_first_screen);



    }
    public void gotoOffline(View view){
        Intent intent=new Intent(this, OfflineActivity.class);
        startActivity(intent);
    }

    public void gotoOnline(View view){
        Intent intent=new Intent(this, OnlineActivity.class);
        startActivity(intent);
    }
    public void goToLogin(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();

    }

}
