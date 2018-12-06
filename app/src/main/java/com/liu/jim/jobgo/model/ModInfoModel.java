package com.liu.jim.jobgo.model;

import com.google.gson.Gson;
import com.liu.jim.jobgo.contract.ModifyInfoContract;
import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.entity.response.result.ModifyInfoResult;
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
 * Created by lenovo on 2018/5/6.
 */

public class ModInfoModel implements ModifyInfoContract.IModInfoModel {

    @Override
    public void modInfo(ModifyInfoRequest modifyInfoRequest, final IHttpCallBack<ModifyInfoResult> callBack) {
        String reqStr = new Gson().toJson(modifyInfoRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .modInfo(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ModifyInfoResult>() {
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
                    public void onNext(ModifyInfoResult modifyInfoResult) {
                        callBack.onSuccess(modifyInfoResult);
                    }
                });
    }
}
