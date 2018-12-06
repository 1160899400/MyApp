package com.liu.jim.jobgo.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/24.
 */

public class JobApplyRequest {
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("jobId")
    @Expose
    private int jobId;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
