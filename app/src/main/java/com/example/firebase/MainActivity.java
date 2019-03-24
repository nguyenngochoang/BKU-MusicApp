package com.example.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {


    EditText editText ;
    Aleart aleart;

    public DatabaseReference mDatabase;


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
//        setContentView(R.layout.content_user_information);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        Intent getIntent = getIntent();
//        nameSaved = getIntent.getStringExtra("nameV");
        onActivityResult(1,RESULT_OK,getIntent);

        editText = findViewById(R.id.name);




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



    public void onGetData(View view) {
        final EditText name = findViewById(R.id.name);
        EditText pass = findViewById(R.id.pass);

        final String nameV = name.getText().toString();

        String passV = pass.getText().toString();
        if(nameV.isEmpty() || passV.isEmpty()){
            aleart = new Aleart(1,getString(R.string.wrongInfo),MainActivity.this,"","");

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
            }


            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }

            final String passValue = passV;


            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    if (dataSnapshot.hasChild(nameV)){
                        User user = dataSnapshot.child(nameV).getValue(User.class);
//                        Log.w("vcl",passValue);
                        if(user.password.equals(passValue)){
                            aleart = new Aleart(2,getString(R.string.successful)+" "+user.name,MainActivity.this,"","");
                            goToInfoPage(user);

                        }
                        else{
                            aleart = new Aleart(1,getString(R.string.wrongInfo),MainActivity.this,"","");
//
                        }


                    }
                    else{
                        aleart = new Aleart(1," ",MainActivity.this,"","");

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
