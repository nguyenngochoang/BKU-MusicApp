package com.example.firebase;

import android.content.Context;
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
import android.widget.Toast;

import com.Activity.OnlineActivity;
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

public class MainMenu extends AppCompatActivity {

    User user;
    long rowCount = 0;
    public DatabaseReference mDatabase;
    ArrayList<User> row;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

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
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_5);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_5);
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
                        ongotoOnlineMusic();
                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.playlist))
                .normalTextRes(R.string.onlinemusic);

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

        //-------------------- end of adding and styling buttons ---------------------------------

        // ------------ if user are admin, they will have this option ----------------------------

            if(user.role.equals("admin")){
                HamButton.Builder builder5 = new HamButton.Builder()
                        .listener(new OnBMClickListener() {
                            @Override
                            public void onBoomButtonClick(int index) {
                                onGotoAdminPage();
                            }
                        })
                        .normalImageDrawable(getResources().getDrawable(R.drawable.admin))
                        .normalTextRes(R.string.admin);

                bmb.addBuilder(builder5);
            }

        // ------------- end of admin ------------------------------------------------------------

    }

    public void ongotoOnlineMusic(){
        Intent intent = new Intent(MainMenu.this, OnlineActivity.class);
        intent.putExtra("userInfo",user);
        startActivity(intent);
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

    public void goToMyPlaylist(View view){
        Intent intent = new Intent(MainMenu.this,Playlist.class);
        intent.putExtra("userInfo",user);
        startActivity(intent);
    }
}


