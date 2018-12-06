package com.liu.jim.jobgo.presenter.job_info;

import android.widget.Toast;

import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.contract.job_info.JobDataByKwContract;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.job_info.JobDataByKwModel;

/**
 * Created by lenovo on 2018/4/27.
 */

public class JobDataByKwPresenter implements JobDataByKwContract.IJobDataByKwPresenter {
    private int page = 1;
    private int total = 0;
    private JobDataByKwContract.IJobDataByKwView mIJobDataByKwView;
    private JobDataByKwContract.IJobDataByKwModel mIJobDataByKwModel;

    public JobDataByKwPresenter(JobDataByKwContract.IJobDataByKwView mIJobDataByKwView) {
        this.mIJobDataByKwView = mIJobDataByKwView;
        this.mIJobDataByKwModel = new JobDataByKwModel();
    }

    @Override
    public void getJobDataByKw(String keyword) {
        page = 1;
        mIJobDataByKwModel.getJobDataByKw(keyword, new IHttpCallBack<JobListResult>() {
            @Override
            public void onSuccess(JobListResult jobListResult) {
                if (jobListResult == null && jobListResult.getData() != null) {
                    mIJobDataByKwView.showJobDataByKw(null);
                } else {
                    total = jobListResult.getData().getData().getTotal();
                    Toast.makeText(MyApplication.getContext(),"已为您查询到"+total+"个结果",Toast.LENGTH_SHORT).show();
                    mIJobDataByKwView.showJobDataByKw(jobListResult.getData().getData().getJobData());
                    page++;
                }
            }

            @Override
            public void onFail(String errorMsg) {
                mIJobDataByKwView.showJobDataByKw(null);
            }
        });


    }

    @Override
    public void updateJobDataByKw(String key) {
        page = 1;
        getJobDataByKw(key);
    }

    @Override
    public void loadMore(String key) {
        page ++;
        if (page*10-10>total){
            Toast.makeText(MyApplication.getContext(),"抱歉，已无更多结果",Toast.LENGTH_SHORT).show();
        }else {
            mIJobDataByKwModel.getJobDataByKw(key, new IHttpCallBack<JobListResult>() {
                @Override
                public void onSuccess(JobListResult jobListResult) {
                    if (jobListResult == null && jobListResult.getData() != null) {
                        mIJobDataByKwView.showJobDataByKw(null);
                    } else {
                        total = jobListResult.getData().getData().getTotal();
                        if (total < page * 10 && total != 0) {
                            mIJobDataByKwView.showJobDataByKw(jobListResult.getData().getData().getJobData().subList(0, page * 10 - total - 1));
                        } else {
                            mIJobDataByKwView.showJobDataByKw(jobListResult.getData().getData().getJobData());
                        }
                        page++;
                    }
                }

                @Override
                public void onFail(String errorMsg) {
                    mIJobDataByKwView.showJobDataByKw(null);
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        mIJobDataByKwView = null;
    }
}
