package com.liu.jim.jobgo.model.user_auth;

import com.google.gson.Gson;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.user_auth.MsgLoginContract;
import com.liu.jim.jobgo.entity.request.MessageLoginRequest;
import com.liu.jim.jobgo.entity.response.result.LoginResult;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;
import com.liu.jim.jobgo.util.HttpExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


/**
 * Created by jim on 2018/4/26.
 */

public class MsgLoginModel implements MsgLoginContract.IMsgLoginModel {

    @Override
    public void loginByMsg(String phone, String msg, int smsId, final IHttpCallBack<LoginResult> callBack) {
        MessageLoginRequest messageLoginRequest = initMsgLognReq(phone, msg, smsId);
        String reqStr = new Gson().toJson(messageLoginRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .msgLogin(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        HttpExceptionUtil.catchHttpException(e, callBack);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        callBack.onSuccess(loginResult);
                    }
                });
    }


    private MessageLoginRequest initMsgLognReq(String phone, String msg, int smsId) {
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
