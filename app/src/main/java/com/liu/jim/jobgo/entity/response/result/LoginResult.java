package com.liu.jim.jobgo.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.data.LoginResultData;

/**
 * 接收login请求后返回的response body的实体
 */

public class LoginResult {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("data")
    @Expose
    private LoginResultData data;
    @SerializedName("token")
    @Expose
    private String token;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public LoginResultData getData() {
        return data;
    }

    public void setData(LoginResultData data) {
        this.data = data;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
