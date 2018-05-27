package com.example.lenovo.myapp.contract.user_auth;

import com.example.lenovo.myapp.entity.response.result.RegisterResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

/**
 * 注册
 */

public class RegContract {
    //视图层
    public interface IRegView {
        public void showRegResult(int resultCode);
    }

    //视图与逻辑处理的业务层
    public interface IRegPresenter {
        void register(String phone,String pwd,int smsId,String message);         //注册
        void onDestroy();           //presenter对应的view消失时调用
    }

    //逻辑业务层，主要为获取后台数据
    public interface IRegModel {
        void register(String phone,String pwd,int smsId,String message, IHttpCallBack<RegisterResult> callBack);
    }
}
