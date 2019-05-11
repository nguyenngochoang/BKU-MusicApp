package com.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song implements Parcelable {

@SerializedName("Idsong")
@Expose
private String idsong;
@SerializedName("Namesong")
@Expose
private String namesong;
@SerializedName("Imagesong")
@Expose
private String imagesong;
@SerializedName("Linksong")
@Expose
private String linksong;
@SerializedName("Singer")
@Expose
private String singer;
@SerializedName("Likecount")
@Expose
private String likecount;



    public Song(String namesong, String imagesong, String linksong, String singer) {
        this.namesong = namesong;
        this.imagesong = imagesong;
        this.linksong = linksong;
        this.singer = singer;
    }

    protected Song(Parcel in) {
        idsong = in.readString();
        namesong = in.readString();
        imagesong = in.readString();
        linksong = in.readString();
        singer = in.readString();
        likecount = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public Song(String namesong, String linksong, String singer) {
        this.namesong = namesong;
        this.linksong = linksong;
        this.singer = singer;
    }

    public String getIdsong() {
return idsong;
}

public void setIdsong(String idsong) {
this.idsong = idsong;
}

public String getNamesong() {
return namesong;
}

public void setNamesong(String namesong) {
this.namesong = namesong;
}

public String getImagesong() {
return imagesong;
}

public void setImagesong(String imagesong) {
this.imagesong = imagesong;
}

public String getLinksong() {
return linksong;
}

public void setLinksong(String linksong) {
this.linksong = linksong;
}

public String getSinger() {
return singer;
}

public void setSinger(String singer) {
this.singer = singer;
}

public String getLikecount() {
return likecount;
}

public void setLikecount(String likecount) {
this.likecount = likecount;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idsong);
        dest.writeString(namesong);
        dest.writeString(imagesong);
        dest.writeString(linksong);
        dest.writeString(singer);
        dest.writeString(likecount);
    }
}
