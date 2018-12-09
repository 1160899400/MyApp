package com.liu.jim.jobgo.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.data.BlankData;

/**
 * Created by jim on 2018/4/14.
 */

public class ModifyInfoResult {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("data")
    @Expose
    private BlankData data;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public BlankData getData() {
        return data;
    }

    public void setData(BlankData data) {
        this.data = data;
    }
}
