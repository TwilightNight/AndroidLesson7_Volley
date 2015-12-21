package com.example.shana.androidlesson7_volley;

/**
 * Created by shana on 2015/12/21.
 */
public class Host {
    public static String getHostName() {
        return "http://Windows11:8888";
    }

    public static String getImageUrl(String imageName) {
        return getHostName() + "/img/" + imageName;
    }
}
