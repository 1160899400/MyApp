package com.example.lenovo.myapp.presenter.job_info;

import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.myapp.contract.job_info.JobDataByCrContract;
import com.example.lenovo.myapp.entity.response.bean.Area;
import com.example.lenovo.myapp.entity.response.bean.JobBasicInfo;
import com.example.lenovo.myapp.entity.response.bean.Screen;
import com.example.lenovo.myapp.entity.response.result.JobListResult;
import com.example.lenovo.myapp.model.inf.IHttpCallBack;
import com.example.lenovo.myapp.model.job_info.JobDataByCrModel;
import com.example.lenovo.myapp.util.MyApplication;

import java.util.List;

/**
 * Created by lenovo on 2018/5/5.
 */

public class JobDataByCrPresenter implements JobDataByCrContract.IJobDataByCrPresenter {
    private int page = 1;
    private boolean hasNext = true;
    private JobDataByCrContract.IJobDataByCrModel mIJobDataByCrModel;
    private JobDataByCrContract.IJobDataByCrView mIJobDataByCrView;

    public JobDataByCrPresenter(JobDataByCrContract.IJobDataByCrView mIJobDataByCrView) {
        this.mIJobDataByCrView = mIJobDataByCrView;
        mIJobDataByCrModel = new JobDataByCrModel();
    }

    @Override
    public void startScreen(List<String> jobTypes, Area area, Screen screen) {
        page = 1;
        mIJobDataByCrModel.getJobDataByCr(new IHttpCallBack<JobListResult>() {
            @Override
            public void onSuccess(JobListResult jobListResult) {
                List<JobBasicInfo> jbi;
                if (jobListResult == null || jobListResult.getData() == null || jobListResult.getData().getData() == null) {
                    mIJobDataByCrView.showJobDataByCr(null);
                    Toast.makeText(MyApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                } else {
                    hasNext = jobListResult.getData().getData().isHasNext();
                    Log.i("###hasNext","" + hasNext + " " +jobListResult.getData().getData().getTotal());
                    jbi = jobListResult.getData().getData().getJobData();
                    if (jbi.size() == 0) {       //数据大小为空，则从缓存获取
                        Toast.makeText(MyApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                    }
                    page ++;
                    mIJobDataByCrView.showJobDataByCr(jbi);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                Toast.makeText(MyApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                mIJobDataByCrView.showJobDataByCr(null);
                Log.i("###jobcr req", errorMsg);
            }
        }, jobTypes, area, screen, page);
    }

    @Override
    public void refreshList(List<String> jobTypes, Area area, Screen screen) {
        if (page >= 2) {     //调整请求的页码
            page = page / 2;
        }
        hasNext = true;     //调整是否有下一页的判断
        mIJobDataByCrModel.getJobDataByCr(new IHttpCallBack<JobListResult>() {
            @Override
            public void onSuccess(JobListResult jobListResult) {
                List<JobBasicInfo> jbi;
                if (jobListResult == null || jobListResult.getData() == null || jobListResult.getData().getData() == null) {
                    mIJobDataByCrView.showJobDataByCr(null);
                    Toast.makeText(MyApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                } else {
                    jbi = jobListResult.getData().getData().getJobData();
                    hasNext = jobListResult.getData().getData().isHasNext();
                    if (jbi.size() == 0) {
                        Toast.makeText(MyApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                    }
                    mIJobDataByCrView.showJobDataByCr(jbi);
                }
            }
            @Override
            public void onFail(String errorMsg) {
                Toast.makeText(MyApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                mIJobDataByCrView.showJobDataByCr(null);
                Log.i("###jobcr req", errorMsg);
            }
        }, jobTypes, area, screen, page);
    }

    @Override
    public void LoadMore(List<String> jobTypes, Area area, Screen screen) {
        if (hasNext){
            mIJobDataByCrModel.getJobDataByCr(new IHttpCallBack<JobListResult>() {
                @Override
                public void onSuccess(JobListResult jobListResult) {
                    List<JobBasicInfo> jbi;
                    if (jobListResult == null || jobListResult.getData() == null || jobListResult.getData().getData() == null) {
                        mIJobDataByCrView.showJobDataByCr(null);
                        Toast.makeText(MyApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
                    } else {
                        hasNext = jobListResult.getData().getData().isHasNext();
                        jbi = jobListResult.getData().getData().getJobData();
                        if (jbi.size() == 0) {       //数据大小为空，则从缓存获取
                            Toast.makeText(MyApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
                        }
                        page ++;
                        mIJobDataByCrView.showJobDataByCr(jbi);
                    }
                }
                @Override
                public void onFail(String errorMsg) {
                    Toast.makeText(MyApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                    mIJobDataByCrView.showJobDataByCr(null);
                }
            }, jobTypes, area, screen, page);
        }else {
            mIJobDataByCrView.showJobDataByCr(null);
            Toast.makeText(MyApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        mIJobDataByCrView = null;
    }
}
