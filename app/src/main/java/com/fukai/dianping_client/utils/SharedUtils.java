package com.fukai.dianping_client.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.GestureDetector;

/**
 * Created by fukai on 2015/11/24.
 */
//实现标记的读取和写入
public class SharedUtils {
    private static final String FILE_NAME =  "dianping";
    private static final String MODE_NAME = "welcome";
    //提取boolean的值
    public static boolean getWelcomeBoolean(Context context){
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
                .getBoolean(MODE_NAME, true);
    }
    //写入boolean的值
    public static void putWelcomeBoolean(Context context,boolean isFirst){
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME,Context.MODE_APPEND)
                .edit();
        editor.putBoolean(MODE_NAME,isFirst);
        editor.commit();
    }

    public static String getCityName(Context context){
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
                .getString("cityName","选择城市");
    }
    public static void putCityName(Context context, String cityName){
        context.getSharedPreferences(FILE_NAME,Context.MODE_APPEND)
                .edit()
                .putString("cityName",cityName)
                .commit();
    }

}
