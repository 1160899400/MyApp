package com.liu.jim.jobgo.presenter.job_info;

import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.contract.job_info.JobDataByDisContract;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.job_info.JobDataByDisModel;

import java.util.LinkedList;
import java.util.List;

/**
 * 获取附近兼职的presenter
 */

public class JobDataByDisPresenter implements JobDataByDisContract.IJobDataByDisPresenter {
    private int page = 1;     //jobBasicInfo的页数
    private JobDataByDisContract.IJobDataByDisModel mIJobDataByDisModel;
    private JobDataByDisContract.IJobDataByDisView mIJobDataByDisView;

    public JobDataByDisPresenter(JobDataByDisContract.IJobDataByDisView mIJobDataByDisView) {
        this.mIJobDataByDisView = mIJobDataByDisView;
        this.mIJobDataByDisModel = new JobDataByDisModel();
    }


    /**
     * 获取附近的jobBasicInfo
     */
    @Override
    public void getJobDataByDis() {
        mIJobDataByDisModel.getJobDataByDis(AppConstants.position, page, new IHttpCallBack<JobListResult>() {
            @Override
            public void onSuccess(JobListResult jlr) {   //成功后回调，解析数据
                List<JobBasicInfo> jbi;
                if (jlr == null) {
                    jbi = readJobDisFromCache();  //从缓存获取
                } else {
                    jbi = jlr.getData().getData().getJobData();
                    if (jbi.size() == 0) {       //数据大小为空，则从缓存获取
                        jbi = readJobDisFromCache();
                    } else {
                        //将数据异步写入缓存
                        page ++;
                        writeJobDisToCache(jbi);
                    }
                }
                mIJobDataByDisView.showJobDataByDis(jbi);
            }

            @Override
            public void onFail(String errorMsg) {
                Toast.makeText(MyApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                Log.i("###job dis", "解析问题2");
                List<JobBasicInfo> jbi = readJobDisFromCache();
                mIJobDataByDisView.showJobDataByDis(jbi);
                Log.i("###jobDisReq", errorMsg);
            }
        });
        page++;
    }

    /**
     * 刷新附近的jobBasicInfo
     */
    @Override
    public void refreshJobDataByDis() {
        page = page / 2;
        page++;
        getJobDataByDis();
    }


    /**
     * 从缓存中获取附近兼职
     */
    @Nullable
    private List<JobBasicInfo> readJobDisFromCache() {
        ACache aCache = ACache.get(MyApplication.getContext(), CacheConstants.DIR_JOB_LIST_DIS);
        String jobListStr = aCache.getAsString(CacheConstants.JOB_LIST_DIS);
        LinkedList<JobBasicInfo> newJobLs = new Gson().fromJson(jobListStr, new TypeToken<LinkedList<JobBasicInfo>>() {
        }.getType());
        return newJobLs;
    }

    /**
     * 将附近兼职异步写入缓存
     */
    private void writeJobDisToCache(List<JobBasicInfo> jbi) {
        CacheManager.getCacheManager().asyWrite(CacheConstants.JOB_LIST_DIS, new Gson().toJson(jbi), CacheConstants.DIR_JOB_LIST_DIS);
    }

    @Override
    public void onDestroy() {
        mIJobDataByDisView = null;
    }
}
