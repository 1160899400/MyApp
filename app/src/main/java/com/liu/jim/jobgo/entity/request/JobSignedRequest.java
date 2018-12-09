package com.liu.jim.jobgo.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jim on 2018/4/21.
 */

public class JobSignedRequest {
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("token")
    @Expose
    private String token;

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
}
