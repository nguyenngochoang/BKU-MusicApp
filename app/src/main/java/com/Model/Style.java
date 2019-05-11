package com.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Style implements Serializable {

@SerializedName("Idstyle")
@Expose
private String idstyle;
@SerializedName("Namestyle")
@Expose
private String namestyle;
@SerializedName("Imagestyle")
@Expose
private String imagestyle;

public String getIdstyle() {
return idstyle;
}

public void setIdstyle(String idstyle) {
this.idstyle = idstyle;
}

public String getNamestyle() {
return namestyle;
}

public void setNamestyle(String namestyle) {
this.namestyle = namestyle;
}

public String getImagestyle() {
return imagestyle;
}

public void setImagestyle(String imagestyle) {
this.imagestyle = imagestyle;
}

}