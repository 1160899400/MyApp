package com.example.lenovo.myapp.presenter.user_auth;

import android.util.Log;

import com.example.lenovo.myapp.constants.AppConstants;
import com.example.lenovo.myapp.constants.CacheConstants;
import com.example.lenovo.myapp.contract.user_auth.MsgLoginContract;
import com.example.lenovo.myapp.entity.response.bean.LoginPersonalInfo;
import com.example.lenovo.myapp.entity.response.result.LoginResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.model.user_auth.MsgLoginModel;
import com.example.lenovo.myapp.util.ACache;
import com.example.lenovo.myapp.util.MyApplication;

/**
 * Created by lenovo on 2018/4/26.
 */

public class MsgLoginPresenter implements MsgLoginContract.IMsgLoginPresenter {

    private MsgLoginContract.IMsgLoginModel mIMsgLoginModel;
    private MsgLoginContract.IMsgLoginView mIMsgLoginView;


    public MsgLoginPresenter(MsgLoginContract.IMsgLoginView mIMsgLoginView){
        this.mIMsgLoginView = mIMsgLoginView;
        this.mIMsgLoginModel = new MsgLoginModel();
    }

    @Override
    public void loginByMsg(String phone, String msg, int smsId) {
        mIMsgLoginModel.loginByMsg(phone, msg, smsId, new IHttpCallBack<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult == null){
                    Log.i("msgLogin","loginResult为空");
                }else {
                    anlLoginResult(loginResult);
                }
            }
            @Override
            public void onFail(String errorMsg) {
                Log.i("msgLogin",errorMsg);
            }
        });

    }

    /**
     * 对返回的密码登录结果进行解析
     * @param loginResult
     */
    private void anlLoginResult(LoginResult loginResult){
        int resultCode = loginResult.getResult().getCode();
        if (resultCode == 1) {
            String token = loginResult.getToken();
            LoginPersonalInfo lpi = loginResult.getData().getData();
            ACache aCache = ACache.get(MyApplication.getContext());
            if (token != null && lpi != null) {     //写入缓存
                aCache.put(CacheConstants.LOGIN_TOKEN, token);
                CacheManager.getCacheManager().putPersonalInfo(lpi);
            }
            AppConstants.loginStatus = true;            //更改全局登录状态
        }
        mIMsgLoginView.showLoginResult(resultCode);
    }

    @Override
    public void onDestroy() {
        mIMsgLoginView = null;
    }
}
