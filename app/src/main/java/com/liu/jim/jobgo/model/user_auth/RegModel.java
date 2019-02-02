package com.liu.jim.jobgo.model.user_auth;

import com.google.gson.Gson;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.user_auth.RegContract;
import com.liu.jim.jobgo.entity.request.RegisterRequest;
import com.liu.jim.jobgo.entity.response.result.RegisterResult;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;
import com.liu.jim.jobgo.util.EncryptUtil;
import com.liu.jim.jobgo.util.HttpExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by jim on 2018/4/26.
 */

public class RegModel implements RegContract.IRegModel {
    @Override
    public void register(String phone, String pwd, int smsId, String message, final IHttpCallBack<RegisterResult> callBack) {
        RegisterRequest registerRequest = initRegReq(phone,pwd,smsId,message);
        String reqStr = new Gson().toJson(registerRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .register(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RegisterResult>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        HttpExceptionUtil.catchHttpException(e, callBack);
                    }

                    @Override
                    public void onComplete() {

                    }



                    @Override
                    public void onNext(RegisterResult registerResult) {
                        callBack.onSuccess(registerResult);
                    }
                });
    }

    private RegisterRequest initRegReq(String phone, String password, int smsId, String message){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setAccountPhone(phone);
        String pwd = EncryptUtil.Companion.encryptPassword(password);       //对密码进行加密
        registerRequest.setAccountPassword(pwd);
        registerRequest.setSmsId(smsId);
        registerRequest.setCode(message);
        registerRequest.setVersion(AppConstants.VERSION);
        return registerRequest;
    }
}
