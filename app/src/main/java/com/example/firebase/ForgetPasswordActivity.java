package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pankaj.mail_in_background.LongOperation;
;

import java.security.MessageDigest;
import java.util.Random;

/**
 * A login screen that offers login via email/password.
 */
public class ForgetPasswordActivity extends AppCompatActivity  {

    private String getForgetEmail;
    private String getBody;
    private EditText email;
    private EditText name;
    private TextView f_name_err;
    private TextView f_email_err;
    private String newPassWord;
    private Alert alert;

    public DatabaseReference mDatabase;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        context = this;

        email = findViewById(R.id.f_email);
        name = findViewById(R.id.f_name);
        f_email_err = findViewById(R.id.f_email_error);
        f_name_err = findViewById(R.id.f_name_error);

        email.addTextChangedListener(etw);
    }

    private boolean isEmailValid(CharSequence email) {
        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            TextView tv = findViewById(R.id.f_email_error);
            tv.setText(getResources().getString(R.string.error_invalid_email));
            return false;
        }
        return true;

    }
    TextWatcher etw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isEmailValid(email.getText().toString());
            TextView tv = findViewById(R.id.f_email_error);
            tv.setText(" ");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public String randomPasswordGenerator(){
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        return sb1.toString();
    }
    public void sendEmail(){
        newPassWord = randomPasswordGenerator();
        Log.w("T1",newPassWord);
        String body = "Mật khẩu mới của bạn là:" + newPassWord;
        getForgetEmail = email.getText().toString();
        getBody = body;
        try
        {
            LongOperation l=new LongOperation(getForgetEmail,getBody);
            l.execute();  //sends the email in background
            Toast.makeText(this, l.get(), Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }


    }

    public void onGoback(boolean check){
        final Intent intent = new Intent(this, MainActivity.class);
        Handler handler = new Handler();
        Runnable my_runnable = new Runnable() {
            @Override
            public void run() {
                intent.putExtra("nameV",name.getText().toString());
                startActivity(intent);
            }
        };
        if(check){
            handler.postDelayed( my_runnable,1000);

        }

    }

    public void onSetNewPassword(final String nameV, final String emailV){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(nameV)){
                    User user = dataSnapshot.child(nameV).getValue(User.class);
                    if(user.email.equals(emailV)){
                        sendEmail();
                        Log.w("T2",newPassWord);
                        try{
                            // Create MessageDigest instance for MD5
                            MessageDigest md = MessageDigest.getInstance("MD5");
                            //Add password bytes to digest
                            md.update(newPassWord.getBytes());
                            //Get the hash's bytes
                            byte[] bytes = md.digest();
                            //This bytes[] has bytes in decimal format;
                            //Convert it to hexadecimal format
                            StringBuilder sb = new StringBuilder();
                            for(int i=0; i< bytes.length ;i++)
                            {
                                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                            }
                            //Get complete hashed password in hex format
//                            Log.w("T3",newPassWord);
                            newPassWord = sb.toString();
                        }catch (Exception e){
                            Log.e("",e.getMessage());
                        }

                        mDatabase.child(nameV).child("password").setValue(newPassWord);
                        alert = new Alert(2,getString(R.string.passwordGenerated),ForgetPasswordActivity.this,"nameV",nameV);
                    }
                    else{
                        f_email_err.setText(getResources().getString(R.string.emailNotFound));
                    }
                }
                else{
                    f_name_err.setText(getResources().getString(R.string.infoNotFound));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void onGetPassword(View view){
        boolean checkValidEmail = isEmailValid(email.getText().toString());
        if(!checkValidEmail){
            return;
        }
        else{
            String nameV = name.getText().toString();
            String emailV = email.getText().toString();
            onSetNewPassword(nameV,emailV);
        }
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        email.setText("");
        name.setText("");
    }



}

