package com.liu.jim.jobgo.model.job_info;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.liu.jim.jobgo.contract.job_info.JobDetailContract;
import com.liu.jim.jobgo.entity.request.JobDetailRequest;
import com.liu.jim.jobgo.entity.response.result.JobDetailResult;
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
 * Created by jim on 2018/4/26.
 */

public class JobDetailModel implements JobDetailContract.IJobDetailModel {
    @Override
    public void getJobDetail(int accountId, String token, int jobId, final IHttpCallBack<JobDetailResult> callBack) {
        JobDetailRequest jobDetailRequest = initJobDetailReq(accountId,token,jobId);
        String reqStr = new GsonBuilder().serializeNulls().create().toJson(jobDetailRequest);
        Log.i("###jobDetailReq",reqStr);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getJobDetail(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<JobDetailResult>() {
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
                    public void onNext(JobDetailResult jobDetailResult) {
                        callBack.onSuccess(jobDetailResult);
                    }
                });
    }

    private JobDetailRequest initJobDetailReq(int accountId, String token, int jobId){
        JobDetailRequest jobDetailRequest = new JobDetailRequest();
        jobDetailRequest.setAccountId(accountId);
        jobDetailRequest.setToken(token);
        jobDetailRequest.setJobId(jobId);
        return jobDetailRequest;
    }
}
