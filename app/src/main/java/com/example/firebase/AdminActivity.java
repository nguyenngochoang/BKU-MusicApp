
package com.example.firebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    private Context context = null;
    public long rowCount;
    ArrayList <User> row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();


        row = (ArrayList<User>)intent.getSerializableExtra("userArray");
        rowCount =intent.getLongExtra("rowCount",1L);

        String[] column = { "Tên người dùng", "Email", "Ngày sinh",
                "Giới tính", "Loại tài khoản","Khoá"
        };

        int cl=column.length;

        ScrollView sv = new ScrollView(this);
        TableLayout tableLayout = loadUserList(row, column,cl);
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        hsv.addView(tableLayout);
        sv.addView(hsv);
        setContentView(sv);




    }



    public TableLayout loadUserList(final ArrayList<User> row, String [] cv, int columnCount){

        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(this);


        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(5, 5, 5, 5);
        tableRowParams.weight = 1;

        int count = 1;

        for (int i = 0; i < rowCount; i++)
        {
            // 3) create tableRow
            TableRow tableRow = new TableRow(this);


            for (int j= 0; j <= columnCount; j++)
            {
                // 4) create textView
                TextView textView = new TextView(this);
                EditText editText = new EditText(this);
                editText.setBackgroundColor(Color.TRANSPARENT);
                editText.setGravity(Gravity.CENTER);
                editText.setEnabled(false);
                editText.setTag(Integer.toString(i)+j);

                //  textView.setText(String.valueOf(j));
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);



                if (i ==0 && j==0)
                {
                    textView.setText("No/Info");
                    tableRow.addView(textView, tableRowParams);
                }
                else if(i==0)
                {

                    textView.setText(cv[j-1]);
                    tableRow.addView(textView, tableRowParams);
                }
                else if( j==0)
                {

                    textView.setText(Integer.toString(count));
                    tableRow.addView(textView, tableRowParams);
                    count = count +1;
                }
                else
                {
                    // "Tên người dùng", "Email", "Ngày sinh","Giới tính", "Loại tài khoản",
                    ;
                    switch (j){
                        case 1:
                            editText.setText(row.get(i).name);
                            tableRow.addView(editText, tableRowParams);
                            break;
                        case 2:
                            editText.setText(row.get(i).email);
                            tableRow.addView(editText, tableRowParams);
                            break;
                        case 3:
                            editText.setText(row.get(i).date);
                            tableRow.addView(editText, tableRowParams);
                            break;
                        case 4:
                            editText.setText(row.get(i).gender);
                            tableRow.addView(editText, tableRowParams);
                            break;
                        case 5:
                            editText.setText(row.get(i).role);
                            tableRow.addView(editText, tableRowParams);
                            break;

                        case 6:
                            final Button btn=new Button(this);
                            btn.setTag(1);
                            final int temp = i;
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(row.get(temp).name).hasChild("isBlocked")){
                                        if(dataSnapshot.child(row.get(temp).name).child("isBlocked").getValue().toString().equals("true")){
                                            btn.setText("Mở khoá");
                                        }
                                        else{
                                            btn.setText("Khoá");
                                        }
                                    }
                                    else{
                                        btn.setText("Khoá");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            btn.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
                            tableRow.addView(btn, tableRowParams);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ban(row.get(temp),btn);
                                }
                            });
                            break;
                        default:
                            break;

                    }


                }

            }

            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        return tableLayout;


        // --------------------------end of headers ----------------------

    }

    public void ban(final User user, final Button button){
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(user.name)){
                        if(button.getText().toString().equals("Khoá")){
                            mDatabase.child(user.name).child("isBlocked").setValue("true");
                            button.setText("Mở khoá");
                        }
                        else{
                            mDatabase.child(user.name).child("isBlocked").setValue("false");
                            button.setText("Khoá");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
