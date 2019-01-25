package com.liu.jim.jobgo;

import android.app.Application;
import android.content.Context;

/**
 * Android Manifest 配置程序启动时默认初始化该类
 */

public class MyApplication extends Application{
    private  static Context context;     //系统上下文对象
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();

    }

    public static Context getContext() {
        return context;
    }
}
