package com.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Album implements Serializable {

@SerializedName("Idalbum")
@Expose
private String idalbum;
@SerializedName("Namealbum")
@Expose
private String namealbum;
@SerializedName("Namesingeralbum")
@Expose
private String namesingeralbum;
@SerializedName("Imagealbum")
@Expose
private String imagealbum;

public String getIdalbum() {
return idalbum;
}

public void setIdalbum(String idalbum) {
this.idalbum = idalbum;
}

public String getNamealbum() {
return namealbum;
}

public void setNamealbum(String namealbum) {
this.namealbum = namealbum;
}

public String getNamesingeralbum() {
return namesingeralbum;
}

public void setNamesingeralbum(String namesingeralbum) {
this.namesingeralbum = namesingeralbum;
}

public String getImagealbum() {
return imagealbum;
}

public void setImagealbum(String imagealbum) {
this.imagealbum = imagealbum;
}

}