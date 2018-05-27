package com.example.lenovo.myapp.model.job_info;

import android.util.Log;

import com.example.lenovo.myapp.constants.AppConstants;
import com.example.lenovo.myapp.contract.job_info.JobDataByKwContract;
import com.example.lenovo.myapp.entity.request.JobKeywordRequest;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.inf.IHttpService;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.manager.RetrofitManager;
import com.example.lenovo.myapp.util.MyApplication;
import com.google.gson.GsonBuilder;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2018/4/27.
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
                .subscribe(new Subscriber<JobListResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
