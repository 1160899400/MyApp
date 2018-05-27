package com.example.lenovo.myapp.presenter.user_auth;

import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.myapp.constants.AppConstants;
import com.example.lenovo.myapp.constants.CacheConstants;
import com.example.lenovo.myapp.contract.user_auth.PwdLoginContract;
import com.example.lenovo.myapp.entity.response.bean.LoginPersonalInfo;
import com.example.lenovo.myapp.entity.response.result.LoginResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.model.user_auth.PwdLoginModel;
import com.example.lenovo.myapp.util.ACache;
import com.example.lenovo.myapp.util.MyApplication;

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
