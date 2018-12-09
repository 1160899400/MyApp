package com.liu.jim.jobgo.model.job_info;

import com.google.gson.GsonBuilder;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.contract.job_info.JobSignedContract;
import com.liu.jim.jobgo.entity.request.JobSignedRequest;
import com.liu.jim.jobgo.entity.response.result.JobSignedResult;
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
                .subscribe(new Observer<JobSignedResult>() {
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

    private JobSignedRequest initJobSignedReq() {
        JobSignedRequest jobSignedRequest = new JobSignedRequest();
        CacheManager cm = CacheManager.getCacheManager();
        jobSignedRequest.setAccountId(cm.getAccountId(MyApplication.getContext()));
        jobSignedRequest.setToken(cm.getToken(MyApplication.getContext()));
        cm = null;
        return jobSignedRequest;
    }
}
