package com.liu.jim.jobgo.presenter.job_info;

import android.widget.Toast;

import com.liu.jim.jobgo.JobGoApplication;
import com.liu.jim.jobgo.contract.job_info.JobDetailContract;
import com.liu.jim.jobgo.db.model.Job;
import com.liu.jim.jobgo.entity.response.result.JobDetailResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.job_info.JobDetailModel;

/**
 * @author Jim.Liu
 * Created by jim on 2018/4/26.
 */

public class JobDetailPresenter implements JobDetailContract.IJobDetailPresenter {

    private JobDetailContract.IJobDetailModel mIJobDetailModel;
    private JobDetailContract.IJobDetailView mIJobDetailView;

    public JobDetailPresenter(JobDetailContract.IJobDetailView mIJobDetailView){
        this.mIJobDetailView = mIJobDetailView;
        mIJobDetailModel = new JobDetailModel();
    }

    @Override
    public void getJobDetail(int accountId, String token, int jobId) {
        mIJobDetailModel.getJobDetail(accountId, token, jobId, new IHttpCallBack<JobDetailResult>() {
            @Override
            public void onSuccess(JobDetailResult jobDetailResult) {
                if (jobDetailResult == null){
                    mIJobDetailView.showJobDetail(null);
                    Toast.makeText(JobGoApplication.getContext(),"请检查网络设置后重试",Toast.LENGTH_SHORT).show();
                }else {
                    Job jobDetail = jobDetailResult.getJobDetailData().getJobDetail();
                    mIJobDetailView.showJobDetail(jobDetail);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                mIJobDetailView.showJobDetail(null);
                Toast.makeText(JobGoApplication.getContext(),"请检查网络设置后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        mIJobDetailView = null;
    }
}
