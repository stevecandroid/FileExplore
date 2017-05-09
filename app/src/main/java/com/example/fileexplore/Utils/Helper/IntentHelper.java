package com.example.fileexplore.Utils.Helper;

import android.content.Intent;

import com.example.fileexplore.Activity.MyApplication.MyApplication;

/**
 * Created by 铖哥 on 2017/5/8.
 */

public class IntentHelper {

    public static void startActivity(Class<?> activity){
        MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(),activity));
    }

    public static void startActivity(Class<?> activity,int Flag){
        Intent intent = new Intent(MyApplication.getContext(),activity);
        intent.addFlags(Flag);
        MyApplication.getContext().startActivity(intent);
    }

    public static void startService(Class<?> service){
        MyApplication.getContext().startService(new Intent(MyApplication.getContext(),service));
    }

    public static void sendBoardCast(String action){
        MyApplication.getContext().sendBroadcast(new Intent(action));
    }

}
