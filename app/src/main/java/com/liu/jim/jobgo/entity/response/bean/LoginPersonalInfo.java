package com.liu.jim.jobgo.entity.response.bean;

/**
 * Created by jim on 2018/4/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.liu.jim.jobgo.db.model.Account;

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
    @SerializedName("account")
    @Expose
    private Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
