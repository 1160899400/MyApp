package com.liu.jim.jobgo.model;

import com.google.gson.Gson;
import com.liu.jim.jobgo.contract.ApplyJobContract;
import com.liu.jim.jobgo.entity.request.JobApplyRequest;
import com.liu.jim.jobgo.entity.response.result.JobApplyResult;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;
import com.liu.jim.jobgo.util.HttpExceptionUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by lenovo on 2018/4/26.
 */

public class ApplyJobModel implements ApplyJobContract.IApplyJobModel {
    @Override
    public void applyJob(int accountId, String token, int jobId, final IHttpCallBack<JobApplyResult> callBack) {
        JobApplyRequest jobApplyRequest = initJobApplyReq(accountId, token, jobId);
        String reqStr = new Gson().toJson(jobApplyRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .applyJob(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JobApplyResult>() {

                    @Override
                    public void onError(Throwable e) {
                        HttpExceptionUtil.catchHttpException(e, callBack);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JobApplyResult jobApplyResult) {
                        callBack.onSuccess(jobApplyResult);
                    }
                });
    }

    private JobApplyRequest initJobApplyReq(int accountId, String token, int jobId) {
        JobApplyRequest jobApplyRequest = new JobApplyRequest();
        jobApplyRequest.setAccountId(accountId);
        jobApplyRequest.setToken(token);
        jobApplyRequest.setJobId(jobId);
        return jobApplyRequest;
    }

}
