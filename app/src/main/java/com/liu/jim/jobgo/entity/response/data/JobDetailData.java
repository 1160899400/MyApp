package com.liu.jim.jobgo.entity.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.db.model.Job;

/**
 * 用于描述岗位详细信息的data字段
 */

public class JobDetailData {
    @SerializedName("data")
    @Expose
    private Job jobDetail;

    public Job getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(Job jobDetail) {
        this.jobDetail = jobDetail;
    }
}
