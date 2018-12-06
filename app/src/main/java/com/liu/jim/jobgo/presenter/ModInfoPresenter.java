package com.liu.jim.jobgo.presenter;

import com.liu.jim.jobgo.contract.ModifyInfoContract;
import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.entity.response.result.ModifyInfoResult;
import com.liu.jim.jobgo.manager.CacheManager;
import com.liu.jim.jobgo.model.ModInfoModel;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;

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
