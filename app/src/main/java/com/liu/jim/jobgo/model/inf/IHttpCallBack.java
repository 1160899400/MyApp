package com.liu.jim.jobgo.model.inf;

/**
 * Presenter层提供给Model回调的响应接口
 */
public interface IHttpCallBack <T>{
    public void onSuccess(T t);        //请求成功后的回调,将返回的数据传回
    public void onFail(String errorMsg);           //请求失败后回调,并传递错误信息
}
