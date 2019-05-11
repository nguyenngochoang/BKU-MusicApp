package com.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Styletheme {

@SerializedName("Theme")
@Expose
private List<Theme> theme = null;
@SerializedName("Style")
@Expose
private List<Style> style = null;

public List<Theme> getTheme() {
return theme;
}

public void setTheme(List<Theme> theme) {
this.theme = theme;
}

public List<Style> getStyle() {
return style;
}

public void setStyle(List<Style> style) {
this.style = style;
}

}