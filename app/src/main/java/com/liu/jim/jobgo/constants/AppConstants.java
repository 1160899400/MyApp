package com.liu.jim.jobgo.constants;

import com.liu.jim.jobgo.entity.response.bean.Position;

/**
 * 有关系统配置的常量
 */

//http://4083da6.nat123.cc/OrangeServer/
//http://106.14.196.121/OrangeServer/
//http://101.132.40.16/OrangeServer/

public class AppConstants {
    public final static String SERVER_URL = "http://106.14.196.121/OrangeServer/";   //服务器地址
    public final static int version = 2;   //1代表招聘版，2代表求职版，本版本为求职版
    public final static String host = "okHttp";   //请求主机
    public final static String driverType = "ANDROID";   //请求客户端
    public final static String driverId = "1234567898764532";  //加载id
    public static boolean loginStatus = false;    //用户登录状态,true为已登录
    public static Position position = new Position();    //用户定位
    public static String address = null;   //用户地址
    public static final int MY_PERMISSIONS_REQUEST_CALL_CHOOSE_PHONE  = 6;   //打开相册时请求写外存的请求code
    public static final int MY_PERMISSIONS_REQUEST_CALL_TAKE_PHONE  = 7;   //调用摄像头时请求写外存的请求code
}

