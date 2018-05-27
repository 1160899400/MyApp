package com.example.lenovo.myapp.model.job_info;

import android.text.TextUtils;
import android.util.Log;

import com.example.lenovo.myapp.contract.job_info.JobDataHiringContract;
import com.example.lenovo.myapp.entity.request.JobHiringRequest;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.inf.IHttpService;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.manager.RetrofitManager;
import com.example.lenovo.myapp.util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
