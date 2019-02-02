package com.liu.jim.jobgo.presenter.job_info;

import androidx.annotation.Nullable;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liu.jim.jobgo.JobGoApplication;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.contract.job_info.JobDataHiringContract;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.job_info.JobDataHirModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jim on 2018/5/6.
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
                    Toast.makeText(JobGoApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                }else {
                    mIJobDataHirView.showJobDataHir(jobListResult.getData().getData().getJobData());
                    writeJobHirToCache(jobListResult.getData().getData().getJobData());
                }
            }

            @Override
            public void onFail(String errorMsg) {
                mIJobDataHirView.showJobDataHir(readJobHirFromCache());
                Toast.makeText(JobGoApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
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
        ACache aCache = ACache.get(JobGoApplication.getContext(), CacheConstants.DIR_JOB_LIST_HIRING);
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
