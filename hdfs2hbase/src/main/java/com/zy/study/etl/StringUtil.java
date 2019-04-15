package com.zy.study.etl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    public static boolean isStrEmpty(String s){
        return (s == null || "".equals(s));
    }

    public static boolean isStrEmptyWithNull(String s){
        return (s == null || "".equals(s) || "null".equalsIgnoreCase(s));
    }

    public static String formatTimeStampToString(String timeStamp){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((new Date(Long.parseLong(timeStamp) * 1000)));
    }


//    public static void main(String[] arg){
//        String timeStamp = "1493741625";
//        System.out.println(formatTimeStampToString(timeStamp));
//    }
}