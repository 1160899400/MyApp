package com.example.lenovo.myapp.contract.user_auth;

import com.example.lenovo.myapp.entity.response.result.LoginResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

/**
 * 短信验证登录
 */

public class MsgLoginContract {
    //视图层
    public interface IMsgLoginView {
        public void showLoginResult(int resultCode);
    }

    //视图与逻辑处理的业务层
    public interface IMsgLoginPresenter {
        void loginByMsg(String phone, String msg, int smsId);         //通过输入验证码登录
        void onDestroy();           //presenter对应的view消失时调用
    }

    //逻辑业务层，主要为获取后台数据
    public interface IMsgLoginModel {
        void loginByMsg(String phone, String msg, int smsId, IHttpCallBack<LoginResult> callBack);
    }
}
