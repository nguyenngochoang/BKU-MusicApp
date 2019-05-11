package com;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
 
 public static String removeAccent(String s) {

  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
  String temp1 = pattern.matcher(temp).replaceAll("").replace('đ', 'd').trim().replaceAll("\\s+", " ").toLowerCase();

      return temp1;
 }
}