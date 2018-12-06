package com.liu.jim.jobgo.contract;

import com.liu.jim.jobgo.entity.response.result.JobApplyResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

/**
 * 用户报名某一兼职
 */

public class ApplyJobContract {
    //视图层
    public interface IApplyJobView {
        public void showApplyResult(int resultCode);   //显示数据到view上
    }

    //视图与逻辑处理的业务层
    public interface IApplyJobPresenter {
        void applyJob(int accountId, String token, int jobId);
        void onDestroy();           //presenter对应的view消失时调用

    }

    //逻辑业务层，主要为获取后台数据
    public interface IApplyJobModel {
        void applyJob(int accountId, String token, int jobId, IHttpCallBack<JobApplyResult> callBack);      //发送网络请求获取数据
    }

}
