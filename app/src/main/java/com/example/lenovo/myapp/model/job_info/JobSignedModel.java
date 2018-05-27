package com.example.lenovo.myapp.model.job_info;

import android.util.Log;

import com.example.lenovo.myapp.contract.job_info.JobSignedContract;
import com.example.lenovo.myapp.entity.request.JobSignedRequest;
import com.example.lenovo.myapp.entity.response.result.JobSignedResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.inf.IHttpService;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.manager.RetrofitManager;
import com.example.lenovo.myapp.util.MyApplication;
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
 * Created by lenovo on 2018/4/27.
 */

public class JobSignedModel implements JobSignedContract.IJobSignedModel {
    @Override
    public void getJobSigned(final IHttpCallBack<JobSignedResult> callBack) {
        final JobSignedRequest jobSignedRequest = initJobSignedReq();
        String reqStr = new GsonBuilder().serializeNulls().create().toJson(jobSignedRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getJobSigned(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<JobSignedResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callBack.onFail(e.toString());
                    }

                    @Override
                    public void onNext(JobSignedResult jobSignedResult) {
//                        String resstr = null;
//                        try {
//                            resstr = jobSignedResult.string().trim();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        Log.i("###jobSigned",resstr.substring(0,50));
//                        Log.i("###jobSigned",resstr.substring(resstr.length()-50));
                        callBack.onSuccess(jobSignedResult);
                    }
                });
    }

    private JobSignedRequest initJobSignedReq(){
        JobSignedRequest jobSignedRequest = new JobSignedRequest();
        CacheManager cm = CacheManager.getCacheManager();
        jobSignedRequest.setAccountId(cm.getAccountId(MyApplication.getContext()));
        jobSignedRequest.setToken(cm.getToken(MyApplication.getContext()));
        cm = null;
        return jobSignedRequest;
    }
}
