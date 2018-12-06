package com.liu.jim.jobgo.presenter.user_auth;

import android.util.Log;
import android.widget.Toast;

import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.contract.user_auth.PwdLoginContract;
import com.liu.jim.jobgo.entity.response.bean.LoginPersonalInfo;
import com.liu.jim.jobgo.entity.response.result.LoginResult;
import com.liu.jim.jobgo.manager.CacheManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.user_auth.PwdLoginModel;
import com.liu.jim.jobgo.util.ACache;

/**
 * Created by lenovo on 2018/4/26.
 */

public class PwdLoginPresenter implements PwdLoginContract.IPwdLoginPresenter {
    private PwdLoginContract.IPwdLoginModel mIPwdLoginModel;
    private PwdLoginContract.IPwdLoginView mIPwdLoginView;

    public PwdLoginPresenter(PwdLoginContract.IPwdLoginView mIPwdLoginView) {
        this.mIPwdLoginView = mIPwdLoginView;
        this.mIPwdLoginModel = new PwdLoginModel();

    }

    @Override
    public void loginByPwd(String phone, String password) {
        mIPwdLoginModel.loginByPwd(phone, password, new IHttpCallBack<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult == null) {
                    Log.i("###pwdlogin", "loginResult为空");
                } else {
                    anlLoginResult(loginResult);
                }
            }
            @Override
            public void onFail(String errorMsg) {
                Toast.makeText(MyApplication.getContext(),errorMsg,Toast.LENGTH_SHORT).show();
                anlLoginResult(null);
            }
        });
    }

    /**
     * 对返回的密码登录结果进行解析
     * @param loginResult
     */
    private void anlLoginResult(LoginResult loginResult){
        if (loginResult == null){
            mIPwdLoginView.showLoginResult(2);
        }else {
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
            mIPwdLoginView.showLoginResult(resultCode);
        }
    }

    @Override
    public void onDestroy() {
        mIPwdLoginView = null;
    }
}
