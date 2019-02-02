package com.liu.jim.jobgo.db;

import com.liu.jim.jobgo.JobGoApplication;
import com.liu.jim.jobgo.constants.AppConstants;

import io.objectbox.BoxStore;

/**
 * @author Hongzhi.Liu
 * @date 2019/2/2
 *
 * 当前应用的BoxStore的代理类
 */
public class JobGoBoxStore {

    private static JobGoBoxStore sInstance;

    private BoxStore boxStore;

    private JobGoBoxStore(){
        boxStore = MyObjectBox.builder().androidContext(JobGoApplication.getContext()).name().build();
    }

    public JobGoBoxStore getInstance(){
        if (null == sInstance){
            synchronized (JobGoBoxStore.class){
                sInstance = new JobGoBoxStore();
            }
        }
        return sInstance;
    }

}
