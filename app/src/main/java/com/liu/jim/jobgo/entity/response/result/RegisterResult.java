package com.liu.jim.jobgo.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.data.BlankData;

/**
 * 注册请求发送后返回的response body实体
 */

public class RegisterResult {

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
