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
import com.liu.jim.jobgo.util.HttpExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


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
        pwdLoginRequest.setHost(AppConstants.HOST);
        pwdLoginRequest.setLatitude(AppConstants.position.getLat());
        pwdLoginRequest.setLongitude(AppConstants.position.getLon());
        pwdLoginRequest.setDriverType(AppConstants.DRIVER_TYPE);
        pwdLoginRequest.setDriverId(AppConstants.DRIVER_ID);
        pwdLoginRequest.setVersion(AppConstants.VERSION);
        return pwdLoginRequest;
    }
}
