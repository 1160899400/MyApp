package com.liu.jim.jobgo.model.job_info;

import com.google.gson.GsonBuilder;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.constants.AppConstants;
import com.liu.jim.jobgo.contract.job_info.JobDataByCrContract;
import com.liu.jim.jobgo.entity.request.JobCriteriaRequest;
import com.liu.jim.jobgo.entity.response.bean.Area;
import com.liu.jim.jobgo.entity.response.bean.Screen;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.manager.CacheManager;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2018/5/4.
 */

public class JobDataByCrModel implements JobDataByCrContract.IJobDataByCrModel {
    @Override
    public void getJobDataByCr(final IHttpCallBack<JobListResult> callBack, List<String> jobTypes, Area area, Screen screen, int page) {
        JobCriteriaRequest jcr = initJobDataByCrReq(jobTypes, area, screen, page);
        String reqStr = new GsonBuilder().serializeNulls().create().toJson(jcr);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getJobDataByCr(requestBody)
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

    /**
     * 初始化条件查询的请求实体
     */
    private JobCriteriaRequest initJobDataByCrReq(List<String> jobTypes, Area area, Screen screen, int page) {
        JobCriteriaRequest jobCriteriaRequest = new JobCriteriaRequest();
        jobCriteriaRequest.setAccountId(CacheManager.getCacheManager().getAccountId(MyApplication.getContext()));
        jobCriteriaRequest.setToken(CacheManager.getCacheManager().getToken(MyApplication.getContext()));
        jobCriteriaRequest.setPosition(AppConstants.position);
        jobCriteriaRequest.setArea(area);
        jobCriteriaRequest.setScreen(screen);
        jobCriteriaRequest.setType(jobTypes);
        jobCriteriaRequest.setPage(page);
        return jobCriteriaRequest;
    }
}
