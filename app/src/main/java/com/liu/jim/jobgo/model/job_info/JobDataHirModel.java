package com.liu.jim.jobgo.model.job_info;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.contract.job_info.JobDataHiringContract;
import com.liu.jim.jobgo.entity.request.JobHiringRequest;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.manager.CacheManager;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                .subscribe(new Subscriber<JobListResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //失败的时候调用-----一下可以忽略 直接 callBack.onFaild("请求失败");
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFail("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFail("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFail("网络连接超时!!");
                        } else {
                            callBack.onFail("发生未知错误" + e.getMessage());
                        }
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
