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

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.HttpException;

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
                .subscribe(new Observer<RegisterResult>() {

                    @Override
                    public void onSubscribe(Disposable d) {

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
        registerRequest.setVersion(AppConstants.version);
        return registerRequest;
    }
}
