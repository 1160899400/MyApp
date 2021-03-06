package com.liu.jim.jobgo.presenter.job_info;

import android.widget.Toast;

import com.liu.jim.jobgo.MyApplication;
import com.liu.jim.jobgo.contract.job_info.JobSignedContract;
import com.liu.jim.jobgo.entity.response.bean.JobSignedInfo;
import com.liu.jim.jobgo.entity.response.result.JobSignedResult;
import com.liu.jim.jobgo.model.inf.IHttpCallBack;
import com.liu.jim.jobgo.model.job_info.JobSignedModel;

import java.util.List;

/**
 * Created by jim on 2018/4/27.
 */

public class JobSignedPresenter implements JobSignedContract.IJobSignedPresenter {
    private int page = 1;
    private int total = 0;
    private JobSignedContract.IJobSignedView mIJobSignedView;
    private JobSignedContract.IJobSignedModel mIJobSignedModel;

    public JobSignedPresenter(JobSignedContract.IJobSignedView mIJobSignedView) {
        this.mIJobSignedView = mIJobSignedView;
        mIJobSignedModel = new JobSignedModel();
    }

    /**
     * 获取已经报名的工作
     */
    @Override
    public void getJobSigned() {
        if (total > page * 10 - 10 || page == 1) {
            mIJobSignedModel.getJobSigned(new IHttpCallBack<JobSignedResult>() {
                @Override
                public void onSuccess(JobSignedResult jobSignedResult) {
                    if (jobSignedResult != null && jobSignedResult.getData() != null) {
                        List<JobSignedInfo> jobSignedInfoList = jobSignedResult.getData().getData().getRows();
                        if (jobSignedInfoList.size() <= 0) {
                            if (page == 0) {
                                Toast.makeText(MyApplication.getContext(), "你还没有报名任何兼职~", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MyApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
                            }
                            mIJobSignedView.showJobSigned(null);
                        } else {
                            total = jobSignedResult.getData().getData().getTotal();
                            if (total < page * 10 && total != 0){
                                mIJobSignedView.showJobSigned(jobSignedInfoList.subList(0,page*10-total-1));
                            }else {
                                mIJobSignedView.showJobSigned(jobSignedInfoList);
                            }
                        }
                        page++;
                    } else {
                        mIJobSignedView.showJobSigned(null);
                    }
                }

                @Override
                public void onFail(String errorMsg) {
                    mIJobSignedView.showJobSigned(null);
                }
            });
        } else {
            Toast.makeText(MyApplication.getContext(), "没有更多了~", Toast.LENGTH_SHORT).show();
            mIJobSignedView.showJobSigned(null);
        }
    }

    @Override
    public void updateJobSigned() {
        page = 1;
        getJobSigned();
    }

    @Override
    public void onDestroy() {
        mIJobSignedView = null;
    }
}
