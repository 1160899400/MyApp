package com.liu.jim.jobgo.net.client;

import com.liu.jim.jobgo.constants.AppConstants;

/**
 * @author Hongzhi.Liu
 * @date 2019/2/1
 */
public class JobGoClient extends BaseHttpClient {

    private static JobGoClient sInstance;

    public static JobGoClient getsInstance() {
        if (null == sInstance) {
            synchronized (JobGoClient.class){
                if (null == sInstance){
                    sInstance = new JobGoClient();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getBaseUrl() {
        return AppConstants.SERVER_URL;
    }
}
