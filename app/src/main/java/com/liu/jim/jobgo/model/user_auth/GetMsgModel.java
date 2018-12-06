package com.liu.jim.jobgo.model.user_auth;

import com.google.gson.Gson;
import com.liu.jim.jobgo.contract.user_auth.GetMsgContract;
import com.liu.jim.jobgo.entity.request.MessageRequest;
import com.liu.jim.jobgo.entity.response.result.MessageResult;
import com.liu.jim.jobgo.manager.RetrofitManager;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.inf.IHttpService;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.HttpException;

/**
 * Created by lenovo on 2018/4/26.
 */

public class GetMsgModel implements GetMsgContract.IGetMsgModel {
    @Override
    public void getMsg(String phone, byte type, final IHttpCallBack<MessageResult> callBack) {
        MessageRequest messageRequest = initGetMsgReq(phone,type);
        String reqStr = new Gson().toJson(messageRequest);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), reqStr);
        RetrofitManager.getRetrofit()
                .create(IHttpService.class)
                .getMsg(requestBody)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MessageResult>() {

                    @Override
                    public void onSubscribe(Disposable d) {

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
                    public void onComplete() {

                    }



                    @Override
                    public void onNext(MessageResult messageResult) {
                        callBack.onSuccess(messageResult);
                    }
                });
    }

    private MessageRequest initGetMsgReq(String phone,Byte type){
        MessageRequest msgReq = new MessageRequest();
        msgReq.setAccountPhone(phone);
        msgReq.setType(type);
        return msgReq;
    }
}
