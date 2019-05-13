package com.example.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_NORMAL;


public class Playlist extends AppCompatActivity {

    DatabaseReference mDatabase;
    User user;
    String m_Text = "";
    String currentFolderName="";
    Button buttonChanged ;
    String newFolderName="";
    DatabaseReference changeNameDB;
    Button createPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userInfo");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        createPlaylist = findViewById(R.id.button5);
        loadPlaylist();


        createPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewFolder();
            }
        });
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

               //TODO: go inside playlist to view all the songs
                createPlaylistOnDB(name.replaceAll("\\s+",""),name);


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

    public void createPlaylistOnDB(final String Playlistname, final String nodename){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabase.child(user.name).child("playlist").child(nodename).child("name").setValue(Playlistname);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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


    public void loadPlaylist(){

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user.name).hasChild("playlist")){
                    for (DataSnapshot ds : dataSnapshot.child(user.name).child("playlist").getChildren()){
                        String playlistName = ds.getKey().toString();
                        createButton(playlistName);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void changeNameOnDB(final String currentFolderName,final String newFolderName){

        changeNameDB = FirebaseDatabase.getInstance().getReference()
                .child(user.name).child("playlist");
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

    public void deleteFolder(){

    }
}
