package com.liu.jim.jobgo.contract;

import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.entity.response.result.ModifyInfoResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

/**
 * Created by jim on 2018/5/6.
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
