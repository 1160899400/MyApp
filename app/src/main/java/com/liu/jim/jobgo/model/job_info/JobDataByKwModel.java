package com.liu.jim.jobgo.model.job_info;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.job_info.JobDataByKwContract;
import com.liu.jim.jobgo.entity.request.JobKeywordRequest;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.manager.CacheManager;
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
 * Created by jim on 2018/4/27.
 */

public class JobDataByKwModel implements JobDataByKwContract.IJobDataByKwModel {
    @Override
    public void getJobDataByKw(String keyword,  final IHttpCallBack<JobListResult> callBack) {
        JobKeywordRequest jobKeywordRequest = initJobKwReq(keyword);
        String reqStr = new GsonBuilder().serializeNulls().create().toJson(jobKeywordRequest);
        Log.i("###jobDetailReq",reqStr);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getJobDataByKw(requestBody)
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

    private JobKeywordRequest initJobKwReq(String keyword){
        JobKeywordRequest jkwr = new JobKeywordRequest();
        CacheManager cm = CacheManager.getCacheManager();
        int accountId = cm.getAccountId(MyApplication.getContext());
        String token = cm.getToken(MyApplication.getContext());
        cm = null;
        jkwr.setKeywords(keyword);
        jkwr.setAccountId(accountId);
        jkwr.setToken(token);
        jkwr.setLocation(AppConstants.position);
        return jkwr;
    }


}
