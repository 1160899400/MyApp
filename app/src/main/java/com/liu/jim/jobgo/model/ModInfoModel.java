package com.liu.jim.jobgo.model;

import com.google.gson.Gson;
import com.liu.jim.jobgo.contract.ModifyInfoContract;
import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.entity.response.result.ModifyInfoResult;
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
 * Created by jim on 2018/5/6.
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
                .subscribe(new Observer<ModifyInfoResult>() {
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
                    public void onNext(ModifyInfoResult modifyInfoResult) {
                        callBack.onSuccess(modifyInfoResult);
                    }
                });
    }
}
