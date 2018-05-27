package com.example.lenovo.myapp.contract;

import com.example.lenovo.myapp.entity.request.ModifyInfoRequest;
import com.example.lenovo.myapp.entity.response.result.ModifyInfoResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

/**
 * Created by lenovo on 2018/5/6.
 */

public class ModifyInfoContract {
    //视图层
    public interface IModInfoView {
        public void showModInfo(ModifyInfoRequest modifyInfoRequest,int reqType);
        public void modFail(int reqType);
    }

    //视图与逻辑处理的业务层
    public interface IModInfoPresenter {
        void modInfo(ModifyInfoRequest modifyInfoRequest,int reqType);         //修改个人信息
        void onDestroy();           //presenter对应的view消失时调用
    }

    //逻辑业务层，主要为获取后台数据
    public interface IModInfoModel {
        void modInfo(ModifyInfoRequest modifyInfoRequest,IHttpCallBack<ModifyInfoResult> callBack);
    }
}
