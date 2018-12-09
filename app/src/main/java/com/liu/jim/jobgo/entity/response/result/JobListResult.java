package com.liu.jim.jobgo.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.data.JobRowsData;

/**
 * Created by jim on 2018/4/13.
 */

public class JobListResult {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("data")
    @Expose
    private JobRowsData data;
    public void setResult(Result result) {
        this.result = result;
    }
    public Result getResult() {
        return result;
    }

    public void setData(JobRowsData data) {
        this.data = data;
    }
    public JobRowsData getData() {
        return data;
    }

}
