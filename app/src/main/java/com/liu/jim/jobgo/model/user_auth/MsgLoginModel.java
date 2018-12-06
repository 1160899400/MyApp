package com.liu.jim.jobgo.model.user_auth;

import com.google.gson.Gson;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.user_auth.MsgLoginContract;
import com.liu.jim.jobgo.entity.request.MessageLoginRequest;
import com.liu.jim.jobgo.entity.response.result.LoginResult;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2018/4/26.
 */

public class MsgLoginModel implements MsgLoginContract.IMsgLoginModel {

    @Override
    public void loginByMsg(String phone, String msg, int smsId, final IHttpCallBack<LoginResult> callBack) {
        MessageLoginRequest messageLoginRequest = initMsgLognReq(phone,msg,smsId);
        String reqStr = new Gson().toJson(messageLoginRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .msgLogin(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFail("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFail("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFail("网络连接超时!!");
                        } else {
                            callBack.onFail("发生未知错误" + e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        callBack.onSuccess(loginResult);
                    }
                });
    }


    private MessageLoginRequest initMsgLognReq(String phone,String msg,int smsId){
        MessageLoginRequest messageLoginRequest = new MessageLoginRequest();
        messageLoginRequest.setAccountPhone(phone);
        messageLoginRequest.setCode(msg);
        messageLoginRequest.setSmsId(smsId);
        messageLoginRequest.setHost(AppConstants.host);
        messageLoginRequest.setLatitude(AppConstants.position.getLat());
        messageLoginRequest.setLongitude(AppConstants.position.getLon());
        messageLoginRequest.setDriverType(AppConstants.driverType);
        messageLoginRequest.setDriverId(AppConstants.driverId);
        messageLoginRequest.setVersion(AppConstants.version);
        return messageLoginRequest;
    }

}
