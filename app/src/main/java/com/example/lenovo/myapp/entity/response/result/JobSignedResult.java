package com.example.lenovo.myapp.entity.response.result;

import com.example.lenovo.myapp.entity.response.data.JobSignedData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
