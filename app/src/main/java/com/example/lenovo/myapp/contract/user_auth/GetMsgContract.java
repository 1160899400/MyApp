package com.example.lenovo.myapp.contract.user_auth;

import com.example.lenovo.myapp.entity.response.result.MessageResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

/**
 * 获取验证码
 */

public class GetMsgContract {
    //视图层
    public interface IGetMsgView {
        public void showGetMsg(MessageResult messageResult);   //显示数据到view上
    }

    //视图与逻辑处理的业务层
    public interface IGetMsgPresenter {
        void getMsg(String phone,byte type);
        void onDestroy();           //presenter对应的view消失时调用

    }

    //逻辑业务层，主要为获取后台数据
    public interface IGetMsgModel {
        void getMsg(String phone,byte type, IHttpCallBack<MessageResult> callBack);      //发送网络请求获取数据
    }
}
