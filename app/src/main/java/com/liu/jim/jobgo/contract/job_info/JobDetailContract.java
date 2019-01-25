package com.liu.jim.jobgo.contract.job_info;

import com.liu.jim.jobgo.db.model.Job;
import com.liu.jim.jobgo.entity.response.result.JobDetailResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

/**
 * 查看岗位详情
 */

public class JobDetailContract {
    //视图层
    public interface IJobDetailView {
        public void showJobDetail(Job jobDetail);
    }

    //视图与逻辑处理的业务层
    public interface IJobDetailPresenter {
        void getJobDetail(int accountId, String token, int jobId);         //查询兼职详情
        void onDestroy();           //presenter对应的view消失时调用
    }

    //逻辑业务层，主要为获取后台数据
    public interface IJobDetailModel {
        void getJobDetail(int accountId, String token, int jobId, IHttpCallBack<JobDetailResult> callBack);
    }
}
