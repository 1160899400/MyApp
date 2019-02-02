package com.liu.jim.jobgo.model.job_info;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.contract.job_info.JobDataHiringContract;
import com.liu.jim.jobgo.entity.request.JobHiringRequest;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
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
 * 获取招聘中的工作
 */

public class JobDataHirModel implements JobDataHiringContract.IJobDataHirModel {
    @Override
    public void getJobDataHir(int page, final IHttpCallBack<JobListResult> callBack) {
        JobHiringRequest jobHiringRequest = initJobHirReq(page);
        final String reqStr = new GsonBuilder().serializeNulls().create().toJson(jobHiringRequest);
        Log.i("###req jobhir", reqStr);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getJobDataHiring(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JobListResult>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        HttpExceptionUtil.catchHttpException(e, callBack);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JobListResult jobListResult) {
                        callBack.onSuccess(jobListResult);
                    }
                });
    }

    private JobHiringRequest initJobHirReq(int page) {
        JobHiringRequest jobHiringRequest = new JobHiringRequest();
        jobHiringRequest.setToken(CacheManager.getCacheManager().getToken(MyApplication.getContext()));
        jobHiringRequest.setJobState(2);
        jobHiringRequest.setAccountId(CacheManager.getCacheManager().getAccountId(MyApplication.getContext()));
        jobHiringRequest.setPage(page);
        return jobHiringRequest;
    }
}
