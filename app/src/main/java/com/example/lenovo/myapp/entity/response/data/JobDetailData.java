package com.example.lenovo.myapp.entity.response.data;

import com.example.lenovo.myapp.entity.response.bean.JobDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 用于描述岗位详细信息的data字段
 */

public class JobDetailData {
    @SerializedName("data")
    @Expose
    private JobDetail jobDetail;

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }
}
