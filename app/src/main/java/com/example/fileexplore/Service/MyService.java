package com.example.fileexplore.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.fileexplore.Utils.Tools.Log;

/**
 * Created by 铖哥 on 2017/5/8.
 */

public class MyService extends Service {

    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder{
        public void  read(){
            Log.line();
        }
    }


}
