package com.example.lenovo.myapp.contract.job_info;

import com.example.lenovo.myapp.entity.response.bean.JobDetail;
import com.example.lenovo.myapp.entity.response.result.JobDetailResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

/**
 * 查看岗位详情
 */

public class JobDetailContract {
    //视图层
    public interface IJobDetailView {
        public void showJobDetail(JobDetail jobDetail);
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
