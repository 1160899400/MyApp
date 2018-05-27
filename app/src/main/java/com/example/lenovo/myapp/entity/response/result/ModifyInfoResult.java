package com.example.lenovo.myapp.entity.response.result;

import com.example.lenovo.myapp.entity.response.data.BlankData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/14.
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
