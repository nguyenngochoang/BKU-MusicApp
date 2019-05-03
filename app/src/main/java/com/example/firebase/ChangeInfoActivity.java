package com.example.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChangeInfoActivity extends AppCompatActivity {

    EditText c_email;
    EditText c_birthday;
    EditText c_nickname;

    RadioGroup RG ;
    int selectedID ;
    RadioButton genderB ;

    EditText c_pass;
    EditText c_re_pass;
    TextView passValidate;

    String username;
    public DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().setTitle(getResources().getString(R.string.updateInfo));

        passValidate = findViewById(R.id.c_pass_validate);

        c_nickname = findViewById(R.id.c_nickname_field);
        c_email = findViewById(R.id.c_email_field);
        c_birthday = findViewById(R.id.c_birthday_field);
        RG = findViewById(R.id.c_gender_group);
        c_pass = findViewById(R.id.c_pass_field);
        c_re_pass = findViewById(R.id.c_pass_field_val);


        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedID = RG.getCheckedRadioButtonId();
                genderB = findViewById(selectedID);
            }
        });

        Intent intent = getIntent();
        username = intent.getStringExtra("nameV");

    }

    private boolean isBirthdayValid(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;

        if(date.isEmpty()){
            return true;
        }
        else{
            try {
                testDate = dateFormat.parse(date);
            } catch (ParseException pe) {
                c_birthday.setText("");
                c_birthday.setHintTextColor(Color.RED);
                c_birthday.setHint(getResources().getString(R.string.invalidDate));
                return false;
            }

            if(!dateFormat.format(testDate).equals(date)){
                c_birthday.setText("");
                c_birthday.setHintTextColor(Color.RED);
                c_birthday.setHint(getResources().getString(R.string.invalidDate));
                return false;
            }
        }
       return  true;
    }
    private boolean isEmailValid(CharSequence email) {
        if(email.length()==0){
            return true;
        }
        else{
            if( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                c_email.setText("");
                c_email.setHintTextColor(Color.RED);
                c_email.setHint(getResources().getString(R.string.error_invalid_email));
                return false;
            }
        }

        return true;

    }

    public void goToInfoPage(final User user){
        final Intent intent = new Intent(this, UserInformation.class);
        Handler handler = new Handler();
        Runnable my_runnable = new Runnable() {
            @Override
            public void run() {
                intent.putExtra("userInfo",user);
                Toast.makeText(ChangeInfoActivity.this,getString(R.string.infochanged),Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
        handler.postDelayed( my_runnable,1000);
    }

    public void update(final String username, String updateField, String value){
        try{
            if(!value.isEmpty()){
                if(updateField=="password"){
                    try {
                        // Create MessageDigest instance for MD5
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        //Add password bytes to digest
                        md.update(value.getBytes());
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
                        value = sb.toString();

                    }

                    catch (NoSuchAlgorithmException e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        mDatabase.child(username).child(updateField).setValue(value);
                    }
                }

                else {
                    mDatabase.child(username).child(updateField).setValue(value);
                }
            }

        }catch (Exception e){
//            e.printStackTrace();
        }

    }

    public boolean isMatch(String A,String B){
        if(A.equals(B)){

            return true;
        }
        else return false;
    }

    public void onChangeData(View view){
        String email = c_email.getText().toString();
        String birthday = c_birthday.getText().toString();
        String gender= "";
        String nickname = c_nickname.getText().toString();
        if(genderB!=null){
           gender  = genderB.getText().toString();
        }

        boolean check = isBirthdayValid(birthday) && isEmailValid(email);

        Map<String,String> map = new HashMap<String,String>();
        map.put("email",email);
        map.put("date",birthday);
        map.put("gender",gender);
        map.put("nickname",nickname);


        final String pass = c_pass.getText().toString();
        String newPassVal = c_re_pass.getText().toString();

        if(isMatch(pass,newPassVal)){
            passValidate.setText("");
            map.put("password",pass);
           for(Map.Entry<String,String> entry : map.entrySet()){
               if(entry.getValue()!=null &&  check==true ){
                   update(username,entry.getKey(),entry.getValue());
                   mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           User user = dataSnapshot.child(username).getValue(User.class);
                           goToInfoPage(user);

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }

                   });
               }

           }
        }
        else{
            passValidate.setText(getResources().getString(R.string.passwordmatch));
        }

    }


    public void onCancel(View view){
        finish();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
