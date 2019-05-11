package com.Service;

//import com.example.silverp.musicdemo.Model.AdverTising;
//import com.example.silverp.musicdemo.Model.Album;
//import com.example.silverp.musicdemo.Model.Playlist;
//import com.example.silverp.musicdemo.Model.Song;
//import com.example.silverp.musicdemo.Model.Styletheme;

import com.Model.AdverTising;
import com.Model.Album;
import com.Model.Playlist;
import com.Model.Song;
import com.Model.Styletheme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("themestylesong.php")
    Call<Styletheme> Getstylethemetoday();
    @GET("songbanner.php")
    Call<List<AdverTising>> GetDataBanner();
    @GET("playlistsong.php")
    Call<List<Playlist>> GetPlaylistDay();
    @GET("albumsong.php")
    Call<List<Album>> GetAlbumhot();
    @GET("songlike.php")
    Call<List<Song>> GetSonglike();
    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Song>> GetSongadvertising(@Field("idadvertising") String idadvertising);
    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Song>> GetSongplaylist(@Field("idplaylist") String adplaylist);
    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Song>> GetSongtheme(@Field("idtheme") String idtheme);
    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Song>> GetSongstyle(@Field("idstyle") String idstyle);
    @FormUrlEncoded
    @POST("listsong.php")
    Call<List<Song>> GetSongalbum(@Field("idalbum") String idalbum);
    @FormUrlEncoded
    @POST("seachsong.php")
    Call<List<Song>> GetSongseach(@Field("tukhoa") String tukhoa);
    @GET("allplaylist.php")
    Call<List<Playlist>> Getallplaylist();
    @GET("allthemestyle.php")
    Call<Styletheme> Getallstyletheme();
    @GET("allalbum.php")
    Call<List<Album>> Getallalbum();
}
