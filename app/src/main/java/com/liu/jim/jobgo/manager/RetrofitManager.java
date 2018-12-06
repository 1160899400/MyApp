package com.liu.jim.jobgo.manager;

import com.liu.jim.jobgo.constants.AppConstants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2018/4/5.
 * 网络请求的管理类，所有http请求通过此类发送
 * 使用retrofix进行http请求处理
 */

public class RetrofitManager {
    private static Retrofit retrofit;
    private RetrofitManager(){

    }
    public static Retrofit getRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.SERVER_URL)   //设置网络请求的服务器 Url
                .addConverterFactory(GsonConverterFactory.create())  //设置使用Gson解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
