package com.liu.jim.jobgo.presenter.user_auth;

import android.util.Log;

import com.liu.jim.jobgo.contract.user_auth.RegContract;
import com.liu.jim.jobgo.entity.response.result.RegisterResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.user_auth.RegModel;

/**
 * Created by lenovo on 2018/4/26.
 */

public class RegPresenter implements RegContract.IRegPresenter {
    private RegContract.IRegView mIRegView;
    private RegContract.IRegModel mIRegModel;

    public RegPresenter(RegContract.IRegView mIRegView){
        this.mIRegView = mIRegView;
        this.mIRegModel = new RegModel();
    }
    @Override
    public void register(String phone, String pwd, int smsId, String message) {
        mIRegModel.register(phone, pwd, smsId, message, new IHttpCallBack<RegisterResult>() {
            @Override
            public void onSuccess(RegisterResult registerResult) {
                if (registerResult == null){
                    Log.i("###RegRequest","request为空");
                }else {
                    int resultCode = registerResult.getResult().getCode();
                    mIRegView.showRegResult(resultCode);
                }
            }
            @Override
            public void onFail(String errorMsg) {
                Log.i("###RegRequest",errorMsg);
            }
        });
    }

    @Override
    public void onDestroy() {
        mIRegView = null;
    }
}
