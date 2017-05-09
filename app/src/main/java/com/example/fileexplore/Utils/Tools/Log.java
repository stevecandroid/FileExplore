package com.example.fileexplore.Utils.Tools;

/**
 * Created by 铖哥 on 2017/5/8.
 */

public class Log {

    public static boolean enabled = true;

    public static void e(String tag, Object msg) {
        if (enabled)
            android.util.Log.e(tag, msg.toString());
    }

    public static void d(String tag, Object msg) {
        if (enabled)
            android.util.Log.d(tag, msg.toString());
    }

    public static void e(Object msg) {
        if (enabled)
            android.util.Log.e("@@@@@@@@@@", msg.toString());
    }

    public static void d(Object msg) {
        if (enabled)
            android.util.Log.d("@@@@@@@@@@", msg.toString());
    }

    public static void line() {
        if (enabled)
            android.util.Log.e("TEST LINE --------", "------------------------");
    }


}
