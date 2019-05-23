package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.Activity.OfflineActivity;
import com.Activity.OnlineActivity;

import java.util.Locale;

import static com.example.firebase.MainActivity.PREF_NAME;

public class FirstScreen extends AppCompatActivity {



    TextView tvlanguage;
    static Boolean checklanguagevi=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar

        setContentView(R.layout.activity_first_screen);
        tvlanguage=findViewById(R.id.engvi);
        tvlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ganngongu();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });




    }

    private void ganngongu() throws Throwable {
        Locale locale;
        if(checklanguagevi==false) {
            locale = new Locale("vi");
            checklanguagevi=true;
        }
        else
        {
            locale=new Locale("en");
            checklanguagevi=false;
        }
        Configuration configuration =new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(
                configuration,
                getBaseContext().getResources().getDisplayMetrics()
        );
        finish();
        Intent intent=new Intent(FirstScreen.this,FirstScreen.class);
        startActivity(intent);

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
