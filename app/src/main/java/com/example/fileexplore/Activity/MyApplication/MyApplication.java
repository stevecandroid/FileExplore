package com.example.fileexplore.Activity.MyApplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.example.fileexplore.Utils.Tools.Log;

import java.util.Stack;

/**
 * Created by 铖哥 on 2017/5/8.
 */

public class MyApplication extends Application {

    private Stack<Activity> mActivityStack = new Stack<>();
    private static Context mContext;

    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();


        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivityStack.push(activity);
                Log.e(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

                try{
                    mActivityStack.pop();
                    Log.e(activity);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
