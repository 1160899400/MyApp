package com.liu.jim.jobgo.model.user_auth;

import com.google.gson.Gson;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.user_auth.PwdLoginContract;
import com.liu.jim.jobgo.entity.request.PwdLoginRequest;
import com.liu.jim.jobgo.entity.response.result.LoginResult;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;
import com.liu.jim.jobgo.util.EncryptUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PwdLoginModel implements PwdLoginContract.IPwdLoginModel {
    @Override
    public void loginByPwd(String phone, String password, final IHttpCallBack<LoginResult> callBack) {
        PwdLoginRequest pwdLoginRequest = initPwdLoginReq(phone,password);
        String reqStr = new Gson().toJson(pwdLoginRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .pwdLogin(requestBody)
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

    /**
     * 初始化密码请求实体
     * @param phone
     * @param password
     * @return
     */
    private PwdLoginRequest initPwdLoginReq(String phone,String password){
        PwdLoginRequest pwdLoginRequest = new PwdLoginRequest();
        pwdLoginRequest.setAccountName(phone);
        String encodedPwd = EncryptUtil.Companion.encryptPassword(password);    //对密码进行加密
        pwdLoginRequest.setAccountPasswd(encodedPwd);
        pwdLoginRequest.setHost(AppConstants.host);
        pwdLoginRequest.setLatitude(AppConstants.position.getLat());
        pwdLoginRequest.setLongitude(AppConstants.position.getLon());
        pwdLoginRequest.setDriverType(AppConstants.driverType);
        pwdLoginRequest.setDriverId(AppConstants.driverId);
        pwdLoginRequest.setVersion(AppConstants.version);
        return pwdLoginRequest;
    }
}
