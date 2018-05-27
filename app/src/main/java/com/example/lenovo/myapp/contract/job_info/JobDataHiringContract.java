package com.example.lenovo.myapp.contract.job_info;

import com.example.lenovo.myapp.entity.response.bean.JobBasicInfo;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;

import java.util.List;

/**
 * Created by lenovo on 2018/5/6.
 */

public class JobDataHiringContract {
    //视图层
    public interface IJobDataHirView {
        void showJobDataHir(List<JobBasicInfo> jobBasicInfoList);   //显示数据到view上
    }

    //视图与逻辑处理的业务层
    public interface IJobDataHirPresenter {
        void getJobDataHir();
        void refreshJobDataHir();
        void onDestroy();           //presenter对应的view消失时调用

    }

    //逻辑业务层，主要为获取后台数据
    public interface IJobDataHirModel {
        void getJobDataHir(int page, IHttpCallBack<JobListResult> callBack);      //发送网络请求获取数据
    }
}
