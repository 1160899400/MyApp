package com.example.lenovo.myapp.model.job_info;

import android.util.Log;

import com.example.lenovo.myapp.contract.job_info.JobDataByDisContract;
import com.example.lenovo.myapp.entity.request.JobDisRequest;
import com.example.lenovo.myapp.entity.response.bean.Position;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.inf.IHttpService;
import com.example.lenovo.myapp.manager.RetrofitManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                .subscribe(new Subscriber<JobListResult>() {
                    //发送请求完成时回调
                    @Override
                    public void onCompleted() {
                    }
                    //请求失败时回调
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (e instanceof HttpException) {   //失败的时候调用-----一下可以忽略 直接 callBack.onFaild("请求失败");
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
