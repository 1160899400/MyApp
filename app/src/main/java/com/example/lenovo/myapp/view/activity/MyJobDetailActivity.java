package com.example.lenovo.myapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.constants.CacheConstants;
import com.example.lenovo.myapp.constants.IntentConstants;
import com.example.lenovo.myapp.contract.job_info.JobDetailContract;
import com.example.lenovo.myapp.entity.request.ModifyInfoRequest;
import com.example.lenovo.myapp.entity.response.bean.JobDetail;
import com.example.lenovo.myapp.manager.ActivityManager;
import com.example.lenovo.myapp.manager.NoticeManager;
import com.example.lenovo.myapp.presenter.ApplyJobPresenter;
import com.example.lenovo.myapp.presenter.job_info.JobDetailPresenter;
import com.example.lenovo.myapp.util.ACache;
import com.example.lenovo.myapp.util.MyApplication;
import com.google.gson.Gson;

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
    public void showJobDetail(JobDetail jobDetail) {

    }
}
