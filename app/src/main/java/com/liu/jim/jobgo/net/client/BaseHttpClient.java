package com.liu.jim.jobgo.net.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Hongzhi.Liu
 * @date 2019/1/26
 */
public abstract class BaseHttpClient {

    /**
     * service缓存
     */
    protected HashMap<Class, Object> serviceCache;

    /**
     * 超时限制
     */
    private final static int TIME_OUT = 15;
    protected OkHttpClient okHttpClient;
    protected Retrofit retrofit;

    public BaseHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(getInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(getConverterFactory())
                .build();
    }


    /**
     * 获取base url，可以重写后自定义base url
     *
     * @return
     */
    protected abstract String getBaseUrl();

    /**
     * 获取ConverterFactory，默认为gson的ConverterFactory
     *
     * @return
     */
    protected Converter.Factory getConverterFactory() {
        Gson gson = new GsonBuilder().setLenient().create();
        return GsonConverterFactory.create(gson);
    }

    /**
     * 获取拦截器,默认为null，可继承后重写，自定义添加拦截器
     * @return
     */
    protected Interceptor getInterceptor(){
        return null;
    }


    /**
     * 避免反复创建service，重用service
     *
     * @param service
     * @return
     */
    public synchronized Object getService(Class service) {
        if (null != serviceCache.get(service)) {
            return serviceCache.get(service);
        }
        Object serviceObject = retrofit.create(service);
        serviceCache.put(service, serviceObject);
        return serviceObject;
    }
}
