package com.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdverTising implements Serializable {

@SerializedName("Idadvertising")
@Expose
private String idadvertising;
@SerializedName("Contentadver")
@Expose
private String contentadver;
@SerializedName("Imageadver")
@Expose
private String imageadver;
@SerializedName("Idsong")
@Expose
private String idsong;
@SerializedName("Namesong")
@Expose
private String namesong;
@SerializedName("Imagesong")
@Expose
private String imagesong;

public String getIdadvertising() {
return idadvertising;
}

public void setIdadvertising(String idadvertising) {
this.idadvertising = idadvertising;
}

public String getContentadver() {
return contentadver;
}

public void setContentadver(String contentadver) {
this.contentadver = contentadver;
}

public String getImageadver() {
return imageadver;
}

public void setImageadver(String imageadver) {
this.imageadver = imageadver;
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

}