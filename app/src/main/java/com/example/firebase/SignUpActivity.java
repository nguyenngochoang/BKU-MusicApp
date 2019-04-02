package com.example.firebase;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;



/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    String passValue;
    boolean check;
    public DatabaseReference mDatabase;
    Aleart aleart;

    EditText name ;
    EditText pass ;
    EditText email ;
    EditText date ;

    RadioGroup RG ;
    int selectedID ;
    RadioButton gender ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);
        mDatabase = FirebaseDatabase.getInstance().getReference();


         name = findViewById(R.id.username);
         pass = findViewById(R.id.password);
         email = findViewById(R.id.email);
         date = findViewById(R.id.birthday);
         RG = findViewById(R.id.RG);


        name.addTextChangedListener(ntw);
        pass.addTextChangedListener(ptw);
        email.addTextChangedListener(etw);
        date.addTextChangedListener(btw);

       RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               selectedID = RG.getCheckedRadioButtonId();
               gender = findViewById(selectedID);
               if(gender != null){
                   String genderString = gender.getText().toString();
                   genderCheck(genderString);

               }



               TextView tv = findViewById(R.id.genderError);
               tv.setText(" ");
           }
       });


    }


    private boolean isEmailValid(CharSequence email) {
        if( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            TextView tv = findViewById(R.id.emailError);
            tv.setText(getResources().getString(R.string.error_invalid_email));
            return false;
        }
        return true;

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        if(password.length() < 4){
            TextView tv = findViewById(R.id.wrongPassError);
            tv.setText(getResources().getString(R.string.error_invalid_password));
            return false;
        }
        return true;
    }
    private boolean isBirthdayValid(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;

        try {
            testDate = dateFormat.parse(date);
        } catch (ParseException pe) {
            TextView tv = findViewById(R.id.birthdayError);
            tv.setText(getResources().getString(R.string.invalidDate));
            return false;
        }

        if(!dateFormat.format(testDate).equals(date)){
            TextView tv = findViewById(R.id.birthdayError);
            tv.setText(getResources().getString(R.string.invalidDate));
            return false;
        }
        return true;
    }

    private boolean nameCheck(String name){
        if(name.isEmpty()){
            TextView tv = findViewById(R.id.nameError);
            tv.setText(getResources().getString(R.string.blank));
            return false;
        }
        else{
            TextView tv = findViewById(R.id.nameError);
            tv.setText(" ");

        }
        return true;
    }

    private boolean genderCheck(String gender){
        if(gender==null){
            return false;
        }
        return true;
    }


    TextWatcher ntw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

                nameCheck(name.getText().toString());



        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher etw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isEmailValid(email.getText().toString());
            TextView tv = findViewById(R.id.emailError);
            tv.setText(" ");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher ptw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isPasswordValid(pass.getText().toString());
            TextView tv = findViewById(R.id.wrongPassError);
            tv.setText(" ");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    TextWatcher btw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isBirthdayValid(date.getText().toString());
            TextView tv = findViewById(R.id.birthdayError);
            tv.setText(" ");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    public boolean checkValidInput(String email, String passwword,String date,String name, String gender){
        boolean emailCheck = isEmailValid(email);
        boolean passwordCheck = isPasswordValid(passwword);
        boolean dateCheck = isBirthdayValid(date);
        boolean nameAndGenderCheck = (nameCheck(name)&&genderCheck(gender));
        return (emailCheck && passwordCheck && dateCheck && nameAndGenderCheck);
    }

    public void onGoback(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    public void onSaveData(View view) {


        final String emailV = email.getText().toString();
        final String dateV = date.getText().toString();
        String genderValidation=null;

        try{
            genderValidation = gender.getText().toString();
        }catch (Exception e){
            TextView tv = findViewById(R.id.genderError);
            tv.setText(getResources().getString(R.string.blank));
        }

        final String nameV = name.getText().toString();
        final String genderV = genderValidation;
        String passV = pass.getText().toString();


        if(!checkValidInput(emailV,passV,dateV,nameV,genderV)){
           return;
        }


        else{
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
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format
                passV = sb.toString();
                passValue = passV;
            }

            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            //check if username exists or not
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(nameV)){
                        aleart = new Aleart(1,getString(R.string.error),SignUpActivity.this,"","");

                    }
                    else{
                        try{
                            check = true;
                            User user = new User(nameV, passValue,emailV,dateV,genderV,"user","");
                            String userId = nameV;
                            mDatabase.child(userId).setValue(user);

                        }
                        finally {

                            aleart = new Aleart(2,getString(R.string.registerOK),SignUpActivity.this,"nameV",name.getText().toString());
                        }

                    }

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("Some thing went wrong","Error");
                }
            });






        }

    }

}

