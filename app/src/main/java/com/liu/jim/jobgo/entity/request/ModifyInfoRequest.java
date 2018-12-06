package com.liu.jim.jobgo.entity.request;

/**
 * Created by lenovo on 2018/4/14.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.entity.response.bean.Account;
import com.liu.jim.jobgo.entity.response.bean.Applicant;

public class ModifyInfoRequest {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("account")
    @Expose
    private Account account;
    @SerializedName("applicant")
    @Expose
    private Applicant applicant;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

}

