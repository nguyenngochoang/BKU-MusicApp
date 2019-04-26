package com.example.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

public class Playlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //---------------- create menu -------------------
        BoomMenuButton bmb3 = findViewById(R.id.bmb3);
        bmb3.setButtonEnum(ButtonEnum.Ham);
        bmb3.setPiecePlaceEnum(PiecePlaceEnum.HAM_1);
        bmb3.setButtonPlaceEnum(ButtonPlaceEnum.HAM_1);
        //---------------- end of create menu ------------

        HamButton.Builder builder1 = new HamButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {

                    }
                })
                .normalImageDrawable(getResources().getDrawable(R.drawable.addbutton))
                .normalTextRes(R.string.CreatePlaylist);

        bmb3.addBuilder(builder1);
    }
}
