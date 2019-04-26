package com.example.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

public class MainMenu extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        //---------------- getting userInfo --------------
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo") ;
        //---------------- end of getting userInfo

        //---------------- create menu -------------------
        BoomMenuButton bmb = findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_4);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_4);
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
                        goToPlaylist();
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

                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.bug))
                .normalTextRes(R.string.feedback);

        bmb.addBuilder(builder4);

        //-------------------- end of adding and styling buttons ---------------------------------


    }

    public void goToInfoPage(final User user){
        final Intent intent = new Intent(this, UserInformation.class);

        intent.putExtra("userInfo",user);
        startActivity(intent);

    }

    public void goToPlaylist(){
        Intent intent2 = new Intent(MainMenu.this,Playlist.class);

        startActivity(intent2);
    }
}
