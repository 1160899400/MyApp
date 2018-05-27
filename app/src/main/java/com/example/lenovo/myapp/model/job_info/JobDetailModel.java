package com.example.lenovo.myapp.model.job_info;

import android.util.Log;

import com.example.lenovo.myapp.contract.job_info.JobDetailContract;
import com.example.lenovo.myapp.entity.request.JobDetailRequest;
import com.example.lenovo.myapp.entity.response.result.JobDetailResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.inf.IHttpService;
import com.example.lenovo.myapp.manager.RetrofitManager;
import com.google.gson.GsonBuilder;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2018/4/26.
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
                .subscribe(new Subscriber<JobDetailResult>() {
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
