package com.example.firebase;


import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import android.widget.CheckBox;
import android.widget.EditText;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {


    EditText editText ;
    Aleart aleart;
    CheckBox checkBox;


    public DatabaseReference mDatabase;
    public DatabaseReference autoLoginDatabase;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String PREF_NAME = "prefs";
    public static final String KEY_REMEMBER = "remember";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASS = "password";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String text = data.getStringExtra("nameV");
                if(text!=null){
                    editText.setText(text);
                }

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.rememberBox);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //-------------------------- saved login user -----------------
        autoLoginDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String savedUserName =sharedPreferences.getString(KEY_USERNAME,"");
        String savedPass = sharedPreferences.getString(KEY_PASS,"");

        autoLogin(savedUserName,savedPass);
        //--------------------------end of saved login user-----------

        Intent getIntent = getIntent();

        onActivityResult(1,RESULT_OK,getIntent);

        editText = findViewById(R.id.name);

        //------- change checkBox's color when checked -----------------------------
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {Color.RED, Color.BLACK};
        CompoundButtonCompat.setButtonTintList(checkBox, new ColorStateList(states, colors));
        //-------- end of change checkBox's color when checked ----------------------



    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();

    }

    public void onCreateAccount(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivityForResult(intent,1);
    }

    public void goToInfoPage(final User user){
        final Intent intent = new Intent(this, UserInformation.class);
        Handler handler = new Handler();
        Runnable my_runnable = new Runnable() {
            @Override
            public void run() {
                intent.putExtra("userInfo",user);
                startActivity(intent);
            }
        };
            handler.postDelayed( my_runnable,2000);
    }


    public void saveAutoLogin(String name,String pass){
        if(checkBox.isChecked()){
            editor.putString(KEY_USERNAME, name.trim());
            editor.putString(KEY_PASS, pass.trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();

        }
        else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }

    }

    public void autoLogin(final String userName,final String pass){
        if(sharedPreferences.getBoolean(KEY_REMEMBER,true)){
            autoLoginDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(userName)){
                        User user = dataSnapshot.child(userName).getValue(User.class);

                        if(user!=null && !pass.isEmpty()){
                            if(user.password.equals(pass)){
                                goToInfoPage(user);
                            }
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    public void onGetData(View view) {
        final EditText name = findViewById(R.id.name);
        EditText pass = findViewById(R.id.pass);

        final String nameV = name.getText().toString();

        String passV = pass.getText().toString();
        if(nameV.isEmpty() || passV.isEmpty()){
            aleart = new Aleart(1,getString(R.string.wrongInfo),MainActivity.this,"","");

        }
        else {
            try {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                //Add password bytes to digest
                md.update(passV.getBytes());
                //Get the hash's bytes
                byte[] bytes = md.digest();
                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format

                passV = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            final String passValue = passV;


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(nameV)) {
                        User user = dataSnapshot.child(nameV).getValue(User.class);

                        if (user.password.equals(passValue)) {
                            saveAutoLogin(nameV, user.password);
                            goToInfoPage(user);

                        } else {
                            aleart = new Aleart(1, getString(R.string.wrongInfo), MainActivity.this, "", "");
                            //
                        }


                    } else {
                        aleart = new Aleart(1, getString(R.string.wrongInfo), MainActivity.this, "", "");

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }

    }

    public void onForgetPassword(View view){
        Intent intent = new Intent(this,ForgetPasswordActivity.class);
        startActivityForResult(intent,1);

    }



}
