package com.skillbox.blog.utils;

import java.util.Locale;
import java.util.Random;

public class RandomStringGenerator {

  private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String lower = upper.toLowerCase(Locale.ROOT);
  private static final String digits = "0123456789";
  private static final String alphanum = upper + lower + digits;
  private static StringBuilder stringBuilder;

  public static String randomString(int length) {
    stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(alphanum.charAt(new Random().nextInt(alphanum.length())));
    }
    return stringBuilder.toString();
  }
}
