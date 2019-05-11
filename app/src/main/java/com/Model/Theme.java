package com.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Theme implements Serializable {

@SerializedName("Idtheme")
@Expose
private String idtheme;
@SerializedName("Nametheme")
@Expose
private String nametheme;
@SerializedName("Imagetheme")
@Expose
private String imagetheme;

public String getIdtheme() {
return idtheme;
}

public void setIdtheme(String idtheme) {
this.idtheme = idtheme;
}

public String getNametheme() {
return nametheme;
}

public void setNametheme(String nametheme) {
this.nametheme = nametheme;
}

public String getImagetheme() {
return imagetheme;
}

public void setImagetheme(String imagetheme) {
this.imagetheme = imagetheme;
}

}