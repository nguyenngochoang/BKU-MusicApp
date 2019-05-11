package com.example.firebase;


import android.content.Context;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;
import com.pankaj.mail_in_background.LongOperation;

import java.util.ArrayList;


import static com.example.firebase.MainActivity.KEY_PASS;
import static com.example.firebase.MainActivity.KEY_REMEMBER;
import static com.example.firebase.MainActivity.KEY_USERNAME;
import static com.example.firebase.MainActivity.PREF_NAME;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_NORMAL;


public class MainMenu extends AppCompatActivity {

    User user;
    long rowCount = 0;
    public DatabaseReference mDatabase;
    ArrayList<User> row;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    String m_Text = "";
    String currentFolderName="";
    Button buttonChanged ;
    String newFolderName="";
    DatabaseReference changeNameDB;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //---------------- getting userInfo --------------
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo") ;

        row = new ArrayList<User>();
        //---------------- end of getting userInfo

        //---------------- create menu -------------------
        BoomMenuButton bmb = findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);

        // ------------ if user are admin, they will have this option ----------------------------

        if(user.role.equals("admin")){
            bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_6);
            bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_6);
            HamButton.Builder builder6 = new HamButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            onGotoAdminPage();
                        }
                    })
                    .normalImageDrawable(getResources().getDrawable(R.drawable.admin))
                    .normalTextRes(R.string.admin);

            bmb.addBuilder(builder6);
        }
        else{
            bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
            bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
        }

        // ------------- end of admin ------------------------------------------------------------

        //---------------- end of create menu ------------

        //-------------------- below codes are for adding and styling buttons one by one --------------
        HamButton.Builder builder1 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        goToInfoPage(user);
                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.user))
                .normalTextRes(R.string.userInfo);

        bmb.addBuilder(builder1);

        HamButton.Builder builder2 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.album))
                .normalTextRes(R.string.showAlbum);

        bmb.addBuilder(builder2);

        HamButton.Builder builder3 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.chat))
                .normalTextRes(R.string.chat);

        bmb.addBuilder(builder3);

        HamButton.Builder builder4 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        sendFeedback();
                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.bug))
                .normalTextRes(R.string.feedback);

        bmb.addBuilder(builder4);

        HamButton.Builder builder5 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        createNewFolder();
                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.playlist))
                .normalTextRes(R.string.playlist);

        bmb.addBuilder(builder5);

        //-------------------- end of adding and styling buttons ---------------------------------

        loadPlaylist();


    }


    public void createNewFolder(){
        //create folder in recyclerView and warn users to upload something into it if they dont want that
        // folder disappear in their next login.


        // ------------------------- show dialog for user to type folder name ---------------------


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tạo một thư mực ....");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_NORMAL );

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                createButton(m_Text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
        // ------------------------ end of input dialog ------------------------------------------




    }
    public void createButton(final String name){
        // Create a LinearLayout element
        LinearLayout linearLayout = findViewById(R.id.LN_layout);
        final Button button = new Button(this);
        // Add Buttons
        button.setText(name);
        button.setGravity(Gravity.LEFT|Gravity.CENTER);
        button.setAllCaps(false);
        button.setCompoundDrawablesWithIntrinsicBounds( R.drawable.playlist, 0, 0, 0);
        linearLayout.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onGotoPlaylist(button.getText().toString());
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentFolderName = button.getText().toString();
                buttonChanged = button;
                changeFolderName();
                return true;
            }
        });
    }

    public void loadPlaylist(){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child(user.name).child("playlist").getChildren()){
                    String playlistName = ds.getKey().toString();
                    createButton(playlistName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onGotoPlaylist(final String Playlistname){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child(user.name).hasChild("playlist")){
                    mDatabase.child(user.name).child("playlist").child(Playlistname).child("1").setValue("nope");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Intent intent = new Intent(this,Playlist.class);
        intent.putExtra("userInfo",user);
        intent.putExtra("playlistname",Playlistname);
        startActivity(intent);
    }

    public void changeFolderName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đổi tên thư mục ....");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_NORMAL );

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newFolderName = input.getText().toString();
                if(currentFolderName != newFolderName){
                    buttonChanged.setText(newFolderName);
                    changeNameOnDB(currentFolderName,newFolderName);
                }
                else{
                    buttonChanged.setText(newFolderName+"(1)");
                    changeNameOnDB(currentFolderName,newFolderName+"(1)");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }

    public void changeNameOnDB(final String currentFolderName,final String newFolderName){

        changeNameDB = FirebaseDatabase.getInstance().getReference()
                .child(user.name).child("Images");
        changeNameDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    boolean test = ds.child("name").getValue().toString().equals(currentFolderName);

                    if(test == true){
                        changeNameDB.child(ds.getKey()).child("name").setValue(newFolderName);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void goToInfoPage(final User user){

        final Intent intent = new Intent(this, UserInformation.class);
        intent.putExtra("userInfo",user);
        startActivity(intent);

    }

    public void sendFeedback(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:nngochoangnguyen@gmail.com"));
        intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "" });
        intent.putExtra(Intent.EXTRA_SUBJECT, "App's feedback");

        startActivity(Intent.createChooser(intent, "Feed back"));

    }

    public void onGotoAdminPage(){
        final Intent intent = new Intent(this, AdminActivity.class);


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rowCount = dataSnapshot.getChildrenCount();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    row.add(user);
                }

                intent.putExtra("userArray",row);
                intent.putExtra("rowCount",rowCount);

                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        editor.putBoolean(KEY_REMEMBER, false);
        editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
        editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
        editor.apply();
        finish();
    }
}


