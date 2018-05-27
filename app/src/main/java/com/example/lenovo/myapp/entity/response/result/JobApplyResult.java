package com.example.lenovo.myapp.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/24.
 */

public class JobApplyResult {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
