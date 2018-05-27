package com.example.lenovo.myapp.model.user_auth;

import com.example.lenovo.myapp.constants.AppConstants;
import com.example.lenovo.myapp.contract.user_auth.RegContract;
import com.example.lenovo.myapp.entity.request.RegisterRequest;
import com.example.lenovo.myapp.entity.response.result.RegisterResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.inf.IHttpService;
import com.example.lenovo.myapp.manager.RetrofitManager;
import com.example.lenovo.myapp.util.EncryptUtil;
import com.google.gson.Gson;

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
                .subscribe(new Subscriber<RegisterResult>() {
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
        registerRequest.setVersion(AppConstants.version);
        return registerRequest;
    }
}
