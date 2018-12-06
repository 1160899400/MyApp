package com.liu.jim.jobgo.contract.job_info;

import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

import java.util.List;

/**
 * 根据关键字查询岗位
 */

public class JobDataByKwContract {
    //视图层
    public interface IJobDataByKwView {
        void showJobDataByKw(List<JobBasicInfo> jobBasicInfoList);   //显示数据到view上
    }

    //视图与逻辑处理的业务层
    public interface IJobDataByKwPresenter {
        void getJobDataByKw(String key);
        void updateJobDataByKw(String key);
        void loadMore(String key);
        void onDestroy();           //presenter对应的view消失时调用

    }

    //逻辑业务层，主要为获取后台数据
    public interface IJobDataByKwModel {
        void getJobDataByKw(String keyword, IHttpCallBack<JobListResult> callBack);      //发送网络请求获取数据
    }
}
