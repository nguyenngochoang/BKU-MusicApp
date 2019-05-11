package com.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Playlist implements Serializable {

@SerializedName("Idplaylist")
@Expose
private String idplaylist;
@SerializedName("Nameplaylist")
@Expose
private String nameplaylist;
@SerializedName("Imagebackground")
@Expose
private String imagebackground;
@SerializedName("Imageicon")
@Expose
private String imageicon;


public String getIdplaylist() {
return idplaylist;
}

public void setIdplaylist(String idplaylist) {
this.idplaylist = idplaylist;
}

public String getNameplaylist() {
return nameplaylist;
}

public void setNameplaylist(String nameplaylist) {
this.nameplaylist = nameplaylist;
}

public String getImagebackground() {
return imagebackground;
}

public void setImagebackground(String imagebackground) {
this.imagebackground = imagebackground;
}

public String getImageicon() {
return imageicon;
}

public void setImageicon(String imageicon) {
this.imageicon = imageicon;
}

}