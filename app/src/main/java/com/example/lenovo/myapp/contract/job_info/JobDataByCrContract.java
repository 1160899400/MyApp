package com.example.lenovo.myapp.contract.job_info;

import com.example.lenovo.myapp.entity.response.bean.Area;
import com.example.lenovo.myapp.entity.response.bean.JobBasicInfo;
import com.example.lenovo.myapp.entity.response.bean.Screen;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

import java.util.List;

/**
 * 条件查询岗位
 */

public class JobDataByCrContract {
    //视图层
    public interface IJobDataByCrView {
        public void showJobDataByCr(List<JobBasicInfo> jbi);   //显示数据到view上
    }

    //视图与逻辑处理的业务层
    public interface IJobDataByCrPresenter {
        void startScreen(List<String> jobTypes, Area area, Screen screen);      //开始变化某条件查询
        void LoadMore(List<String> jobTypes, Area area, Screen screen);        //根据原有的条件查询更多
        void refreshList(List<String> jobTypes, Area area, Screen screen);      //根据原有的条件刷新
        void onDestroy();           //presenter对应的view消失时调用
    }

    //逻辑业务层，主要为获取后台数据
    public interface IJobDataByCrModel {
        void getJobDataByCr(IHttpCallBack<JobListResult> callBack,List<String> jobTypes, Area area, Screen screen,int page);      //发送Http请求获取数据
    }
}
