package com.example.lenovo.myapp.presenter.job_info;

import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.myapp.contract.job_info.JobDetailContract;
import com.example.lenovo.myapp.entity.response.bean.JobDetail;
import com.example.lenovo.myapp.entity.response.result.JobDetailResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.job_info.JobDetailModel;
import com.example.lenovo.myapp.util.MyApplication;

/**
 * Created by lenovo on 2018/4/26.
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
                    Toast.makeText(MyApplication.getContext(),"请检查网络设置后重试",Toast.LENGTH_SHORT).show();
                }else {
                    JobDetail jobDetail = jobDetailResult.getJobDetailData().getJobDetail();
                    mIJobDetailView.showJobDetail(jobDetail);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                mIJobDetailView.showJobDetail(null);
                Toast.makeText(MyApplication.getContext(),"请检查网络设置后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        mIJobDetailView = null;
    }
}
