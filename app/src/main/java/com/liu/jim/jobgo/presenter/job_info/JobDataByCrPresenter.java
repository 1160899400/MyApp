package com.liu.jim.jobgo.presenter.job_info;

import android.util.Log;
import android.widget.Toast;

import com.liu.jim.jobgo.JobGoApplication;
import com.liu.jim.jobgo.contract.job_info.JobDataByCrContract;
import com.liu.jim.jobgo.entity.response.bean.Area;
import com.liu.jim.jobgo.entity.response.bean.JobBasicInfo;
import com.liu.jim.jobgo.entity.response.bean.Screen;
import com.liu.jim.jobgo.entity.response.result.JobListResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.job_info.JobDataByCrModel;

import java.util.List;

/**
 * Created by jim on 2018/5/5.
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
                    Toast.makeText(JobGoApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                } else {
                    hasNext = jobListResult.getData().getData().isHasNext();
                    Log.i("###hasNext","" + hasNext + " " +jobListResult.getData().getData().getTotal());
                    jbi = jobListResult.getData().getData().getJobData();
                    if (jbi.size() == 0) {       //数据大小为空，则从缓存获取
                        Toast.makeText(JobGoApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                    }
                    page ++;
                    mIJobDataByCrView.showJobDataByCr(jbi);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                Toast.makeText(JobGoApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(JobGoApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                } else {
                    jbi = jobListResult.getData().getData().getJobData();
                    hasNext = jobListResult.getData().getData().isHasNext();
                    if (jbi.size() == 0) {
                        Toast.makeText(JobGoApplication.getContext(), "没有符合要求的职位~", Toast.LENGTH_SHORT).show();
                    }
                    mIJobDataByCrView.showJobDataByCr(jbi);
                }
            }
            @Override
            public void onFail(String errorMsg) {
                Toast.makeText(JobGoApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(JobGoApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
                    } else {
                        hasNext = jobListResult.getData().getData().isHasNext();
                        jbi = jobListResult.getData().getData().getJobData();
                        if (jbi.size() == 0) {       //数据大小为空，则从缓存获取
                            Toast.makeText(JobGoApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
                        }
                        page ++;
                        mIJobDataByCrView.showJobDataByCr(jbi);
                    }
                }
                @Override
                public void onFail(String errorMsg) {
                    Toast.makeText(JobGoApplication.getContext(), "请检查网络设置后重试", Toast.LENGTH_SHORT).show();
                    mIJobDataByCrView.showJobDataByCr(null);
                }
            }, jobTypes, area, screen, page);
        }else {
            mIJobDataByCrView.showJobDataByCr(null);
            Toast.makeText(JobGoApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        mIJobDataByCrView = null;
    }
}
