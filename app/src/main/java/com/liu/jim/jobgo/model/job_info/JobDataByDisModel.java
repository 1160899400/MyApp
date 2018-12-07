package com.liu.jim.jobgo.model.job_info;

import com.google.gson.Gson;
import com.liu.jim.jobgo.contract.job_info.JobDataByDisContract;
import com.liu.jim.jobgo.entity.request.JobDisRequest;
import com.liu.jim.jobgo.entity.response.bean.Position;
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
 * model层接口的实现
 */

public class JobDataByDisModel implements JobDataByDisContract.IJobDataByDisModel {
    @Override
    public void getJobDataByDis(Position position, int page, final IHttpCallBack<JobListResult> callBack) {
        JobDisRequest jobDisRequest = initJobDisReq(position, page);
        final String reqStr = new Gson().toJson(jobDisRequest);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getJobDataByDis(requestBody)
                .observeOn(AndroidSchedulers.mainThread())      //主线程（ui线程）中订阅
                .subscribeOn(Schedulers.io())           //io线程中执行并返回
                .subscribe(new Observer<JobListResult>() {
                    //发送请求完成时回调
                    @Override
                    public void onComplete() {
                    }
                    //请求失败时回调
                    @Override
                    public void onError(Throwable e) {
                        HttpExceptionUtil.catchHttpException(e, callBack);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    //返回数据时回调
                    @Override
                    public void onNext(JobListResult jobListResult) {
                        callBack.onSuccess(jobListResult);
                    }
                });
    }

    /**
     * 初始化附近职位请求实体
     * @param position
     * @param page
     * @return
     */
    private JobDisRequest initJobDisReq(Position position, int page) {
        JobDisRequest jdr = new JobDisRequest();
        jdr.setPage(page);
        jdr.setLocation(position);
        return jdr;
    }


}
