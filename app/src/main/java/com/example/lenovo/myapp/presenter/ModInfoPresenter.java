package com.example.lenovo.myapp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.myapp.contract.ModifyInfoContract;
import com.example.lenovo.myapp.entity.request.ModifyInfoRequest;
import com.example.lenovo.myapp.entity.response.result.ModifyInfoResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.manager.CacheManager;
import com.example.lenovo.myapp.model.ModInfoModel;
import com.example.lenovo.myapp.util.MyApplication;

/**
 * Created by lenovo on 2018/5/6.
 */

public class ModInfoPresenter implements ModifyInfoContract.IModInfoPresenter {
    private ModifyInfoContract.IModInfoView mIModInfoView;
    private ModifyInfoContract.IModInfoModel mIModInfoModel;

    public ModInfoPresenter(ModifyInfoContract.IModInfoView mIModInfoView) {
        this.mIModInfoView = mIModInfoView;
        this.mIModInfoModel = new ModInfoModel();
    }

    @Override
    public void modInfo(final ModifyInfoRequest modifyInfoRequest, final int ReqType) {
        mIModInfoModel.modInfo(modifyInfoRequest, new IHttpCallBack<ModifyInfoResult>() {
            @Override
            public void onSuccess(ModifyInfoResult modifyInfoResult) {      //获取到后写入缓存
                CacheManager.getCacheManager().putPersonalInfo(modifyInfoRequest);
                ModInfoPresenter.this.mIModInfoView.showModInfo(modifyInfoRequest, ReqType);
            }

            @Override
            public void onFail(String errorMsg) {
                ModInfoPresenter.this.mIModInfoView.modFail(ReqType);
            }
        });
    }

    @Override
    public void onDestroy() {
        this.mIModInfoView = null;
    }
}
