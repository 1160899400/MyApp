package com.liu.jim.jobgo.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.R;
import com.liu.jim.jobgo.constants.CacheConstants;
import com.liu.jim.jobgo.constants.IntentConstants;
import com.liu.jim.jobgo.constants.JobApplyStateConstants;
import com.liu.jim.jobgo.contract.ApplyJobContract;
import com.liu.jim.jobgo.contract.job_info.JobDetailContract;
import com.liu.jim.jobgo.entity.request.ModifyInfoRequest;
import com.liu.jim.jobgo.entity.response.bean.JobDetail;
import com.liu.jim.jobgo.manager.ActivityManager;
import com.liu.jim.jobgo.manager.NoticeManager;
import com.liu.jim.jobgo.presenter.ApplyJobPresenter;
import com.liu.jim.jobgo.presenter.job_info.JobDetailPresenter;
import com.liu.jim.jobgo.util.ACache;

/**
 * Created by lenovo on 2018/4/5.
 */

public class JobDetailActivity extends AppCompatActivity implements View.OnClickListener, JobDetailContract.IJobDetailView, ApplyJobContract.IApplyJobView {

    private ImageView iv_jobType;
    private TextView tv_jobName;   //显示工作名
    private TextView tv_jobState;     //工作状态
    private TextView tv_jobDesc;    //显示工作描述
    private TextView tv_jobDate;    //显示工作起止日期
    private TextView tv_workTime;   //显示工作时间
    private TextView tv_jobPlace;
    private TextView tv_jobAdr;
    private TextView tv_jobCredit;
    private TextView tv_jobNumber;
    private TextView tv_jobUrgent;
    private TextView tv_payType;
    private Button btn_operation;

    private int accountId;
    private String token;
    private int jobId;
    private int appState;

    private JobDetailPresenter jobDetailPresenter;
    private ApplyJobPresenter applyJobPresenter;

    private JobDetail jobDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        bindView();
        jobDetailPresenter = new JobDetailPresenter(this);
        applyJobPresenter = new ApplyJobPresenter(this);
        initJobDetail();
        ActivityManager actManager = ActivityManager.getActManager();
        actManager.addActivity("JobDetailActivity", this);
    }

    /**
     * 初始化详情
     */
    private void initJobDetail() {
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

    /**
     * 绑定视图内的控件
     */
    private void bindView() {
        tv_jobName = findViewById(R.id.tv_job_name);
        tv_jobState = findViewById(R.id.tv_job_state);
        tv_jobPlace = findViewById(R.id.tv_jobPlace);
        tv_jobAdr = findViewById(R.id.tv_jobAddress);
        tv_jobDate = findViewById(R.id.tv_work_date);
        tv_workTime = findViewById(R.id.tv_work_time);
        iv_jobType = findViewById(R.id.iv_jobType);
        tv_payType = findViewById(R.id.tv_jobPayType);
        tv_jobCredit = findViewById(R.id.tv_credit);
        tv_jobNumber = findViewById(R.id.tv_jobNumber);
        tv_jobUrgent = findViewById(R.id.tv_job_urgent);
        tv_jobDesc = findViewById(R.id.tv_jobDesc);
        btn_operation = findViewById(R.id.btn_operation);
    }


    /**
     * 将返回的jobDtail显示
     *
     * @param jobDetail
     */
    @Override
    public void showJobDetail(JobDetail jobDetail) {
        if (jobDetail == null) {
            setContentView(R.layout.item_no_data);
            NoticeManager.build(this).loadFail();        //兼职详情加载完毕
        } else {
            this.jobDetail = jobDetail;
            String jobAdr = jobDetail.getJobAddress();
            String jobStime = jobDetail.getJobStime();
            String jobEtime = jobDetail.getJobEtime();
            String workTime = jobDetail.getJobJobperiod();
            String jobCity = jobDetail.getJobCity();
            String jobHotspot = jobDetail.getJobHotspot();
            String jobAdress = jobDetail.getJobAddress();
            if (jobDetail.getJobName() == null || jobDetail.getJobName().equals("")){
                tv_jobName.setText("工作名称暂无");
            }else {
                tv_jobName.setText(jobDetail.getJobName());
            }
            String state;                                               //工作状态
            if (jobDetail.getJobState() == null) {
                state = "兼职状态：不明";
            } else {
                state = "兼职状态" + jobDetail.getJobState();
            }
            switch (appState) {
                case JobApplyStateConstants.APPLYING:
                    btn_operation.setText("取消报名");
                    state += "/报名中";
                    break;
                case JobApplyStateConstants.HIRED:
                    btn_operation.setText("已被录用");
                    btn_operation.setBackground(new ColorDrawable(Color.GRAY));
                    btn_operation.setClickable(false);
                    state += "/已录用";
                    break;
                case JobApplyStateConstants.INTERVIEWING:
                    btn_operation.setText("等待面试通知");
                    btn_operation.setBackground(new ColorDrawable(Color.GRAY));
                    btn_operation.setClickable(false);
                    state += "/等待面试通知";
                    break;
                case JobApplyStateConstants.WORKING:
                    btn_operation.setText("扫码签到");
                    state += "/进行中";
                    break;
                case JobApplyStateConstants.SETTLING:
                    btn_operation.setText("待结算");
                    btn_operation.setBackground(new ColorDrawable(Color.GRAY));
                    btn_operation.setClickable(false);
                    state += "/待结算";
                    break;
                case JobApplyStateConstants.FINISHED:
                    btn_operation.setText("已完成");
                    btn_operation.setBackground(new ColorDrawable(Color.GRAY));
                    btn_operation.setClickable(false);
                    state += "/已完成";
                    break;
                case JobApplyStateConstants.REJECTED:
                    btn_operation.setText("重新报名");
                    state += "/未录用";
                    break;
                default:            //默认为未报名
                    btn_operation.setText("报名");
                    break;
            }
            btn_operation.setOnClickListener(this);
            tv_jobState.setText(state);
            String jobPlace = "工作地点:";               //工作地点
            if (jobCity == null || jobCity.equals("")) {
                jobPlace += "工作城市不明/";
            } else {
                jobPlace += jobCity + "/";
            }
            if (jobHotspot == null || jobHotspot.equals("")) {
                jobPlace += "地区不明/";
            } else {
                jobPlace += jobHotspot;
            }
            tv_jobPlace.setText(jobPlace);
            if (jobAdress == null) {                    //工作地址
                tv_jobAdr.setText("具体地址：地址不明");
            } else {
                tv_jobAdr.setText("具体地址：" + jobAdr);
            }
            String jobDate = "工作日期:";               //工作日期
            if (jobStime == null) {
                jobDate += "起始日期未定/";
            } else {
                jobDate += jobStime + "/";
            }
            if (jobEtime == null) {
                jobDate += "结束日期未定";
            } else {
                jobDate += jobEtime;
            }
            tv_jobDate.setText(jobDate);
            if (workTime == null) {                     //工作时辰
                tv_workTime.setText("工作时间:未定");
            } else {
                tv_workTime.setText("工作时间:" + workTime);
            }
            if (jobDetail.getJobDescription() == null || jobDetail.getJobDescription().equals("")) {               //工作描述
                tv_jobDesc.setText("工作描述：暂无");
            } else {
                tv_jobDesc.setText("工作描述：" + jobDetail.getJobDescription());
            }

            if (jobDetail.getJobPayType() == null || jobDetail.getJobPayType().equals("") ) {       //结算类型
                tv_payType.setText("薪资结算方式:未定");
            } else {
                tv_payType.setText("薪资结算方式:" + jobDetail.getJobPayType());
            }
            if (jobDetail.getJobCredit() == 0){                        //工作评分
                tv_jobCredit.setText("他人评分:暂无评分");
            }else {
                tv_jobCredit.setText("他人评分:" + String.valueOf(jobDetail.getJobCredit()));
            }
            if (jobDetail.getJobNumber() == 0){         //招聘人数
                tv_jobNumber.setText("招聘人数:不明");
            }else {
                tv_jobNumber.setText("招聘人数:" + jobDetail.getJobNumber());
            }
            if (jobDetail.getJobIsurgent() == null || jobDetail.getJobIsurgent().equals("")){       //招聘紧迫程度
                tv_jobUrgent.setText("招聘热度：未知");
            }else {
                tv_jobUrgent.setText("招聘热度：" + jobDetail.getJobIsurgent());
            }
            switch (jobDetail.getJobWorkType()) {
                case "调研":
                    iv_jobType.setImageResource(R.mipmap.job_type1);
                    break;
                case "送餐员":
                    iv_jobType.setImageResource(R.mipmap.job_type2);
                    break;
                case "促销":
                    iv_jobType.setImageResource(R.mipmap.job_type3);
                    break;
                case "礼仪":
                    iv_jobType.setImageResource(R.mipmap.job_type4);
                    break;
                case "安保":
                    iv_jobType.setImageResource(R.mipmap.job_type5);
                    break;
                case "销售":
                    iv_jobType.setImageResource(R.mipmap.job_type6);
                    break;
                case "服务员":
                    iv_jobType.setImageResource(R.mipmap.job_type7);
                    break;
                case "临时工":
                    iv_jobType.setImageResource(R.mipmap.job_type8);
                    break;
                case "校内":
                    iv_jobType.setImageResource(R.mipmap.job_type9);
                    break;
                case "设计":
                    iv_jobType.setImageResource(R.mipmap.job_type10);
                    break;
                case "文员":
                    iv_jobType.setImageResource(R.mipmap.job_type11);
                    break;
                case "派单":
                    iv_jobType.setImageResource(R.mipmap.job_type12);
                    break;
                case "家教":
                    iv_jobType.setImageResource(R.mipmap.job_type13);
                    break;
                case "演出":
                    iv_jobType.setImageResource(R.mipmap.job_type14);
                    break;
                case "客服":
                    iv_jobType.setImageResource(R.mipmap.job_type15);
                    break;
                case "翻译":
                    iv_jobType.setImageResource(R.mipmap.job_type16);
                    break;
                case "实习":
                    iv_jobType.setImageResource(R.mipmap.job_type17);
                    break;
                case "模特":
                    iv_jobType.setImageResource(R.mipmap.job_type18);
                    break;
                default:
                    iv_jobType.setImageResource(R.mipmap.job_type_unknown);
                    break;
            }
            NoticeManager.build(this).loadSuccess();        //兼职详情加载完毕
        }

    }

    /**
     * 报名按钮的点击事件
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_operation) {          //点击操作按钮
            String jobState = jobDetail.getJobState();
            if (jobState == null || jobState.equals("招聘中")) {        //如果该兼职的状态为空或招聘中，则可以报名
                switch (appState) {
                    case JobApplyStateConstants.APPLYING:       //取消报名
                        break;
                    case JobApplyStateConstants.WORKING:        //签到
                        Intent intent = new Intent(JobDetailActivity.this, CaptureActivity.class);
                        JobDetailActivity.this.startActivity(intent);
                        break;
                    default:                //报名
                        NoticeManager.build(this).setNoticeType(NoticeManager.NoticeType.Applying);
                        NoticeManager.build(this).show();
                        applyJobPresenter.applyJob(accountId, token, jobId);
                        break;

                }
            } else if (jobState == "  ") {            //其他的兼职状态，待补充
            }

        }
    }

    /**
     * 展示报名的反馈
     *
     * @param resultCode 报名结果
     */
    @Override
    public void showApplyResult(int resultCode) {
        if (resultCode == 1) {
            Toast.makeText(this, "报名成功", Toast.LENGTH_SHORT).show();
            NoticeManager.build(this).loadSuccess();
        } else {
            Toast.makeText(this, "您已报名该职位，请勿重复报名", Toast.LENGTH_SHORT).show();
            NoticeManager.build(this).loadFail();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (jobDetailPresenter != null) {
            jobDetailPresenter.onDestroy();
            jobDetailPresenter = null;
            System.gc();
        }
        if (applyJobPresenter != null) {
            applyJobPresenter.onDestroy();
            applyJobPresenter = null;
            System.gc();
        }
    }


}
