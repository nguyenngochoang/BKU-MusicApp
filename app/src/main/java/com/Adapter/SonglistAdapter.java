package com.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.Activity.OnlineActivity;
import com.Activity.Playermusic;
import com.Model.Playlist;
import com.Model.Song;
import com.example.firebase.R;
//import com.example.silverp.musicdemo.Activity.PlaymusicActivity;
//import com.example.silverp.musicdemo.Model.Song;
//import com.example.silverp.musicdemo.R;
import com.example.firebase.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_NORMAL;

public class SonglistAdapter extends RecyclerView.Adapter<SonglistAdapter.ViewHoder> {
    private static final int NEW_MENU_ITEM = Menu.FIRST;
    Context context;
    ArrayList<Song> songs;
    DownloadManager downloadManager;
    User user=OnlineActivity.user;
    String m_Text = "";
    DatabaseReference mDatabase;




    public SonglistAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;

    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_songlist,viewGroup,false);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder viewHoder, final int i) {
        final Song song=songs.get(i);
        viewHoder.txtindex.setText(i+1+"");
        viewHoder.txtname.setText(song.getNamesong());
        viewHoder.txtsinger.setText(song.getSinger());
        viewHoder.imglike.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                MenuBuilder menuBuilder= new MenuBuilder(context);
                MenuInflater menuInflater=new MenuInflater(context);
                menuInflater.inflate(R.menu.songmenu,menuBuilder);

                MenuPopupHelper menuPopupHelper= new MenuPopupHelper(context,menuBuilder,viewHoder.imglike);
                menuPopupHelper.setForceShowIcon(true);
                int order=0;
                SubMenu subMenu=menuBuilder.addSubMenu(R.id.songmenu_groupplaylist,10000,1,"Thêm vào playlist").setIcon(R.drawable.baseline_add_black_18dp);
                if(OnlineActivity.playlists!=null) {
                    if (OnlineActivity.playlists.size() > 0) {
                        for (int j = 0; j < OnlineActivity.playlists.size(); j++) {
                            subMenu.add(1, j, j, OnlineActivity.playlists.get(j)).setIcon(R.drawable.playlist);

                        }
                    }

                        subMenu.add(1, OnlineActivity.playlists.size(),OnlineActivity.playlists.size(),"Thêm playlist" ).setIcon(R.drawable.baseline_add_black_18dp);



                }

                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(final MenuBuilder menu, MenuItem item) {
                        if(item.getItemId()>=0 && item.getItemId()<OnlineActivity.playlists.size())
                        {
                            final String playlistname = OnlineActivity.playlists.get(item.getItemId());
                            final String songid = songs.get(i).getIdsong();
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.child(OnlineActivity.user.name).child("playlist").child(playlistname).hasChild(songid)){
                                        mDatabase.child(OnlineActivity.user.name).child("playlist").child(playlistname).child(songid).setValue(songid);
                                        Toast.makeText(context,"Bạn đã thêm bài hát này vào playlist "+playlistname,Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(context,"Bài hát đã tồn tại",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            return true;
                        }
                        int addsubmenu=OnlineActivity.playlists.size();
                        if(item.getItemId()==addsubmenu)
                        {
                            createNewFolder();
                            return true;
                        }
                        switch (item.getItemId()) {
                            case R.id.songmenu_download:{

                                Toast.makeText(context,"download",Toast.LENGTH_LONG).show();
                                downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri =Uri.parse(song.getLinksong());
                                DownloadManager.Request request=new DownloadManager.Request(uri);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference=downloadManager.enqueue(request);

                                return true;
                            }
//                            case R.id.songmenu_addplaylist:{
//
//                            }
                        }
                        return false;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {

                    }
                });
                menuPopupHelper.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txtindex,txtname,txtsinger;
        ImageView imglike;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            txtindex=itemView.findViewById(R.id.dong_songlist_index);
            txtname=itemView.findViewById(R.id.dong_songlist_namesong);
            txtsinger=itemView.findViewById(R.id.dong_songlist_singer);
            imglike=itemView.findViewById(R.id.dong_songlist_likeimg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, Playermusic.class);
                    intent.putExtra("Song",songs.get(getPosition()));
                    context.startActivity(intent);
                }
            });

        }
    }

    public void createNewFolder(){
        //create folder in recyclerView and warn users to upload something into it if they dont want that
        // folder disappear in their next login.


        // ------------------------- show dialog for user to type folder name ---------------------


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tạo một thư mực ....");

        // Set up the input
        final EditText input = new EditText(context);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_NORMAL );

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child(OnlineActivity.user.name).child("playlist").hasChild(m_Text)){
                            createButton(m_Text);
                            Toast.makeText(context,"Ban đã tạo playlist "+m_Text,Toast.LENGTH_SHORT).show();
                            OnlineActivity.playlists.add(m_Text);
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context,"Playlist đã tồn tại",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
        final Button button = new Button(context);
        // Add Buttons
        button.setText(name);
        button.setGravity(Gravity.LEFT|Gravity.CENTER);
        button.setAllCaps(false);
        button.setCompoundDrawablesWithIntrinsicBounds( R.drawable.playlist, 0, 0, 0);

        createPlaylistOnDB(name.replaceAll("\\s+",""),name);



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

}
