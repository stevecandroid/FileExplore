package com.example.fileexplore.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;

import com.example.fileexplore.CustomView.MotionLock;
import com.example.fileexplore.R;
import com.example.fileexplore.Service.MyService;
import com.example.fileexplore.Utils.Tools.Log;

import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private MyService.MyBinder binder ;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.MyBinder) service;
            Log.e(name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(new Intent(this, MyService.class));
        bindService(intent,connection,BIND_AUTO_CREATE);
        startService(intent);


        final MotionLock motionLock = (MotionLock) findViewById(R.id.lock);

        motionLock.setOnPasswordListener(new MotionLock.OnPasswordListener() {
            @Override
            public void onResult(List<Map<String, Integer>> password) {
                if(motionLock.getPassword().size()==0) {
                    motionLock.setPassword(password);
                    //// TODO: 2017/5/9  
                }

            }
        });

        motionLock.setOnResultListener(new MotionLock.OnResultListener() {
            @Override
            public void onResult(boolean result) {
                if(result){
                    //// TODO: 2017/5/9  
                }else{
                    //// TODO: 2017/5/9  
                }
            }
        });

    }
}
