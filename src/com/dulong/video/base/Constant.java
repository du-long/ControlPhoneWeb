package com.dulong.video.base;

/**
 * Created by dulong on 2017/11/28.
 */

public class Constant {
    public static String BASE_URL(String ipAddress) {
        return "http://" + ipAddress + ":8081/control/";
    }

    public static String VIDEO_URL(String ipAddress, String registrationID) {

        return "http://" + ipAddress + ":8080/vod.html?src=" + "rtmp://" + ipAddress + "/live/" + registrationID;
    }
}
