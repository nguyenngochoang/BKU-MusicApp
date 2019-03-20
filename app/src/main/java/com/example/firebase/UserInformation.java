package com.example.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class UserInformation extends AppCompatActivity {

    User user;
    TextView i_username;
    TextView i_email;
    TextView i_birthday;
    TextView i_gender;
    TextView i_role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo");


        i_username = findViewById(R.id.i_username_field);
        i_email = findViewById(R.id.i_email_field);
        i_birthday = findViewById(R.id.i_birthday_field);
        i_gender = findViewById(R.id.i_gender_field);
        i_role = findViewById(R.id.i_role_field);

        i_username.setText(user.name);
        i_email.setText(user.email);
        i_birthday.setText(user.date);
        i_gender.setText(user.gender);
        i_role.setText(user.role);
    }



}
