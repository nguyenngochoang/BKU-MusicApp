package com.example.firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.example.firebase.MainActivity.KEY_PASS;
import static com.example.firebase.MainActivity.KEY_REMEMBER;
import static com.example.firebase.MainActivity.KEY_USERNAME;
import static com.example.firebase.MainActivity.PREF_NAME;

public class UserInformation extends AppCompatActivity {

    User user;
    TextView i_username;
    EditText i_email;
    EditText i_birthday;
    EditText i_gender;
    TextView i_nickname;
    TextView i_role;
    public ImageView imageView;
    public Uri filePath;
    private String avaUrl;
    private final int PICK_IMAGE_REQUEST = 71;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    FirebaseStorage storage;
    StorageReference storageReference;
    StorageReference loadReference;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_information);



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // ---------getting user info from Login page-----------
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo") ;


        //----------end of getting user info from Login page------

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeData();
            }
        });

        try{
            loadReference = storage.getReference().child(user.name+"/"+"avatars/");
        }
        catch (Exception e){

        }





        String hiddenEmail = user.email.substring(0,10)+"..@..";

        i_username = findViewById(R.id.i_username_field);
        i_email = findViewById(R.id.i_email_field);
        i_birthday = findViewById(R.id.i_birthday_field);
        i_gender = findViewById(R.id.i_gender_field);
        i_role = findViewById(R.id.i_role_field);
        i_nickname = findViewById(R.id.i_nickname_field);
        imageView = findViewById(R.id.avatar);
        imageView.bringToFront();

        i_username.setText(user.name);
        i_email.setText(hiddenEmail);
        i_birthday.setText(user.date);
        i_gender.setText(user.gender);
        i_role.setText(user.role);
        i_nickname.setText(user.nickname);

        // -------------------------- getting user's avatar-------------------------

        if(!user.avatarUrl.isEmpty()){

            avaUrl = user.avatarUrl;
            Glide.with(this)
                    .load(avaUrl)
                    .into(imageView);
        }


        // ---------------------------- end of getting user's avatar ----------------

        //slide effect between activites
        overridePendingTransition(R.xml.slide_in, R.xml.slide_out);
    }

    protected void onLeaveThisActivity() {
        //slide effect
        overridePendingTransition(R.xml.enter_from_left, R.xml.exit_to_right);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this,MainMenu.class);
        intent.putExtra("userInfo",user);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    public void ChangeData(){
        Intent intent = new Intent(this, ChangeInfoActivity.class);
        String username = i_username.getText().toString();
        if(username!=null){
            intent.putExtra("nameV",username);
            startActivity(intent);
        }

    }



    //----------------------------User's avatar-----------------------------------------------------
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh ..."), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

                //upload image to db storage
                uploadImage(filePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
    }
    private void uploadImage(final Uri filePath){

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child(user.name +"/"+"avatars/"+ UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mDatabase.child(user.name).child("avatarUrl").setValue(uri.toString());
                                    mDatabase.child("autoLogin").child("avatarUrl").setValue(uri.toString());
                                }
                            });
                            Toast.makeText(UserInformation.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UserInformation.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    public void onChangeAvatar(View view){
        chooseImage();

    }
     //  -----------------end of User's Avatar------------------------------------------------------

    public void onLogOut(View view){
        editor.putBoolean(KEY_REMEMBER, false);
        editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
        editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
        editor.apply();
        finish();
    }


}
