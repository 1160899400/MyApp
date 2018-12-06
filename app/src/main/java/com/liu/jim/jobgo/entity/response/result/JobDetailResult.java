package com.liu.jim.jobgo.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.data.JobDetailData;

/**
 * 请求岗位详情后返回的response body实体
 */

public class JobDetailResult {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("data")
    @Expose
    private JobDetailData jobDetailData;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public JobDetailData getJobDetailData() {
        return jobDetailData;
    }

    public void setJobDetailData(JobDetailData jobDetailData) {
        this.jobDetailData = jobDetailData;
    }
}
