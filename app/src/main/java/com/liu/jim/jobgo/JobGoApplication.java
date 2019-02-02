package com.liu.jim.jobgo;

import android.app.Application;
import android.content.Context;

/**
 * Android Manifest 配置程序启动时默认初始化该类
 *
 * @author Honzhi.Liu
 */

public class JobGoApplication extends Application {

    private static JobGoApplication context;

    /**
     * 当前所处的登录状态
     */
    private static volatile boolean online;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
//        initSingletons();
        initLogginState();

    }

    public static Context getContext() {
        return context;
    }

    private void initSingletons() {

    }


    private void initLogginState(){

    }

    public static boolean isOnline() {
        return online;
    }
}
