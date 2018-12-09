package com.liu.jim.jobgo.presenter;

import android.util.Log;

import com.liu.jim.jobgo.contract.ApplyJobContract;
import com.liu.jim.jobgo.entity.response.result.JobApplyResult;
import com.liu.jim.jobgo.model.ApplyJobModel;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

/**
 * Created by jim on 2018/4/26.
 */

public class ApplyJobPresenter implements ApplyJobContract.IApplyJobPresenter {
    private ApplyJobContract.IApplyJobView mIApplyJobView;
    private ApplyJobContract.IApplyJobModel mIApplyJobModel;

    public ApplyJobPresenter(ApplyJobContract.IApplyJobView mIApplyJobView){
        this.mIApplyJobView = mIApplyJobView;
        this.mIApplyJobModel = new ApplyJobModel();
    }

    @Override
    public void applyJob(int accountId, String token, int jobId) {
        mIApplyJobModel.applyJob(accountId, token, jobId, new IHttpCallBack<JobApplyResult>() {
            @Override
            public void onSuccess(JobApplyResult jobApplyResult) {
                if (jobApplyResult == null){
                    Log.i("###applyJob","ApplyJobResult为null");
                }else {
                    int resultCode = jobApplyResult.getResult().getCode();
                    mIApplyJobView.showApplyResult(resultCode);
                }
            }
            @Override
            public void onFail(String errorMsg) {
                Log.i("###applyJob",errorMsg);
            }
        });
    }

    @Override
    public void onDestroy() {
        mIApplyJobView = null;
    }
}
