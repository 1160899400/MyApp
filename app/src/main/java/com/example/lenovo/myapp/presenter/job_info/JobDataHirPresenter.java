package com.example.lenovo.myapp.presenter.job_info;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.myapp.constants.CacheConstants;
import com.example.lenovo.myapp.contract.job_info.JobDataHiringContract;
import com.example.lenovo.myapp.entity.response.bean.JobBasicInfo;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.job_info.JobDataHirModel;
import com.example.lenovo.myapp.util.ACache;
import com.example.lenovo.myapp.util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lenovo on 2018/5/6.
 */

public class JobDataHirPresenter implements JobDataHiringContract.IJobDataHirPresenter {
    private int page = 1;
    private JobDataHiringContract.IJobDataHirView mIJobDataHirView;
    private JobDataHiringContract.IJobDataHirModel mIJobDataHirModel;

    public JobDataHirPresenter(JobDataHiringContract.IJobDataHirView mIJobDataByKwView){
        this.mIJobDataHirView = mIJobDataByKwView;
        this.mIJobDataHirModel = new JobDataHirModel();
    }

    @Override
    public void getJobDataHir() {
        this.mIJobDataHirModel.getJobDataHir(page,new IHttpCallBack<JobListResult>() {
            @Override
            public void onSuccess(JobListResult jobListResult) {
                if (jobListResult == null || jobListResult.getData() == null || jobListResult.getData() == null){
                    mIJobDataHirView.showJobDataHir(readJobHirFromCache());
                    Toast.makeText(MyApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                }else {
                    mIJobDataHirView.showJobDataHir(jobListResult.getData().getData().getJobData());
                    writeJobHirToCache(jobListResult.getData().getData().getJobData());
                }
            }

            @Override
            public void onFail(String errorMsg) {
                mIJobDataHirView.showJobDataHir(readJobHirFromCache());
                Toast.makeText(MyApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void refreshJobDataHir() {
        page = page / 2;
        page++;
        getJobDataHir();
    }

    /**
     * 从缓存中获取附近兼职
     */
    @Nullable
    private List<JobBasicInfo> readJobHirFromCache() {
        ACache aCache = ACache.get(MyApplication.getContext(), CacheConstants.DIR_JOB_LIST_HIRING);
        String jobListStr = aCache.getAsString(CacheConstants.JOB_LIST_HIRING);
        LinkedList<JobBasicInfo> newJobLs = new Gson().fromJson(jobListStr, new TypeToken<LinkedList<JobBasicInfo>>() {
        }.getType());
        return newJobLs;
    }

    /**
     * 将附近兼职异步写入缓存
     */
    private void writeJobHirToCache(List<JobBasicInfo> jbi) {
        CacheManager.getCacheManager().asyWrite(CacheConstants.JOB_LIST_HIRING, new Gson().toJson(jbi), CacheConstants.DIR_JOB_LIST_HIRING);
    }


    @Override
    public void onDestroy() {
        mIJobDataHirView = null;
    }

}
