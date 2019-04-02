package com.example.firebase;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        BoomMenuButton bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setButtonEnum(ButtonEnum.SimpleCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_3);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(MainMenu.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        }
                    }).normalImageRes(R.drawable.shape_oval_normal)
                    .rotateImage(false)
                    .shadowEffect(true)
                    .shadowOffsetX(20)
                    .shadowOffsetY(0)
                    .shadowRadius(Util.dp2px(20))
                    .shadowCornerRadius(Util.dp2px(20))
                    .shadowColor(Color.parseColor("#ee000000"))
                    .rippleEffect(true);
            bmb.addBuilder(builder);
        }
    }
}
