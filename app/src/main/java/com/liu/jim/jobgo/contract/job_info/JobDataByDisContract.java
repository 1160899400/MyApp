package com.liu.jim.jobgo.contract.job_info;

import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.bean.Position;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

import java.util.List;

/**
 * 获取附近岗位这一功能的 m、v、p的接口
 */

public class JobDataByDisContract {
    //视图层
    public interface IJobDataByDisView {
        public void showJobDataByDis(List<JobBasicInfo> jbi);   //显示数据到view上
    }

    //视图与逻辑处理的业务层
    public interface IJobDataByDisPresenter {
        void getJobDataByDis();      //获取附近更多兼职信息
        void refreshJobDataByDis();        //重新刷新jobData,将页数重置
        void onDestroy();           //presenter对应的view消失时调用

    }

    //逻辑业务层，主要为获取后台数据
    public interface IJobDataByDisModel {
        void getJobDataByDis(Position position, int page, IHttpCallBack<JobListResult> callBack);      //发送Http请求获取数据
    }
}

