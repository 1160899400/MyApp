package com.liu.jim.jobgo.constants;

import com.liu.jim.jobgo.entity.response.bean.Position;

/**
 * 有关系统配置的常量
 */

//http://4083da6.nat123.cc/OrangeServer/
//http://106.14.196.121/OrangeServer/
//http://101.132.40.16/OrangeServer/

public interface AppConstants {
    /**
     * 服务器地址
     */
    String SERVER_URL = "http://106.14.196.121/OrangeServer/";

    /**
     * 1代表招聘版，2代表求职版，本版本为求职版
     */
    int VERSION = 2;

    /**
     * 请求主机
     */
    String HOST = "okHttp";

    /**
     * 请求客户端
     */
    String DRIVER_TYPE = "ANDROID";

    /**
     * 加载id
     */
    String DRIVER_ID = "1234567898764532";

    /**
     * 用户登录状态,true为已登录
     */
    boolean LOGIN_STATUS = false;

    int MY_PERMISSIONS_REQUEST_CALL_CHOOSE_PHONE = 6;   //打开相册时请求写外存的请求code


    int MY_PERMISSIONS_REQUEST_CALL_TAKE_PHONE = 7;   //调用摄像头时请求写外存的请求code
}

