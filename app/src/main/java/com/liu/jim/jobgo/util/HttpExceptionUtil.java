package com.liu.jim.jobgo.util;

import com.liu.jim.jobgo.model.inf.IHttpCallBack;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * @author Hongzhi.Liu
 * @date 2018/12/7
 */
public class HttpExceptionUtil {

    public static void catchHttpException(Throwable throwable, IHttpCallBack callBack){
        throwable.printStackTrace();
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int code = httpException.code();
            if (code == 500 || code == 404) {
                callBack.onFail("服务器出错");
            }
        } else if (throwable instanceof ConnectException) {
            callBack.onFail("网络断开,请打开网络!");
        } else if (throwable instanceof SocketTimeoutException) {
            callBack.onFail("网络连接超时!!");
        } else {
            callBack.onFail("发生未知错误" + throwable.getMessage());
        }
    }

}
