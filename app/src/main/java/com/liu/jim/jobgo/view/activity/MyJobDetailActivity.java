package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.constants.IntentConstants;
import com.liu.jim.jobgo.contract.job_info.JobDetailContract;
import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.db.model.Job;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.job_info.JobDetailPresenter;
import com.liu.jim.jobgo.util.ACache;

/**
 * 个人工作详情页面
 */

public class MyJobDetailActivity extends AppCompatActivity implements JobDetailContract.IJobDetailView {
    private int accountId;
    private String token;
    private int jobId;
    private int appState;

    private JobDetailContract.IJobDetailPresenter jobDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        bindView();
        jobDetailPresenter = new JobDetailPresenter(this);
        initJobDetail();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("JobDetailActivity", this);
    }

    private void bindView(){

    }


    private void initJobDetail(){
        NoticeManager.build(this).setNoticeType(NoticeManager.NoticeType.Loading);
        NoticeManager.build(this).show();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ACache aCache = ACache.get(MyApplication.getContext());
        token = aCache.getAsString(CacheConstants.LOGIN_TOKEN);
        String pistr = aCache.getAsString(CacheConstants.PERSONAL_INFO);
        if (pistr != null) {
            ModifyInfoRequest mdi = new Gson().fromJson(pistr, ModifyInfoRequest.class);
            accountId = mdi.getAccountId();
        }
        jobId = bundle.getInt(IntentConstants.JOB_ID);
        appState = bundle.getInt(IntentConstants.APPLY_STATE);
        jobDetailPresenter.getJobDetail(accountId, token, jobId);
    }

    @Override
    public void showJobDetail(Job jobDetail) {

    }
}
