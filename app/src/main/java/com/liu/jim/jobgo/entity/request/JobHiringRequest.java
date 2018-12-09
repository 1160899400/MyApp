package com.liu.jim.jobgo.entity.request;

/**
 * Created by jim on 2018/4/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobHiringRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("jobState")
    @Expose
    private int jobState;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getJobState() {
        return jobState;
    }

    public void setJobState(int jobState) {
        this.jobState = jobState;
    }

}
