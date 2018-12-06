package com.liu.jim.jobgo.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.data.JobSignedData;

/**
 * Created by lenovo on 2018/4/21.
 */

public class JobSignedResult {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("data")
    @Expose
    private JobSignedData data;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public JobSignedData getData() {
        return data;
    }

    public void setData(JobSignedData data) {
        this.data = data;
    }

}
