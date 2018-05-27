package com.example.lenovo.myapp.entity.response.bean;

/**
 * Created by lenovo on 2018/4/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPersonalInfo {

    @SerializedName("accountId")
    @Expose
    private int accountId;
    @SerializedName("accountPhone")
    @Expose
    private String accountPhone;
    @SerializedName("accountEmail")
    @Expose
    private String accountEmail;
    @SerializedName("applicant")
    @Expose
    private Applicant applicant;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

}
