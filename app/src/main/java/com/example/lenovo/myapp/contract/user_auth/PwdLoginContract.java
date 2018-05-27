package com.example.lenovo.myapp.contract.user_auth;

import com.example.lenovo.myapp.entity.response.result.LoginResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

/**
 * 密码登录
 */

public class PwdLoginContract {
    //视图层
    public interface IPwdLoginView {
        public void showLoginResult(int resultCode);
    }

    //视图与逻辑处理的业务层
    public interface IPwdLoginPresenter {
        void loginByPwd(String phone, String password);         //通过输入密码登录
        void onDestroy();           //presenter对应的view消失时调用
    }

    //逻辑业务层，主要为获取后台数据
    public interface IPwdLoginModel {
        void loginByPwd(String phone, String password, IHttpCallBack<LoginResult> callBack);
    }
}
