package com.example.lenovo.myapp.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/17.
 */

public class Account {
    @SerializedName("accountName")
    @Expose
    private String accountName;
    @SerializedName("accountEmail")
    @Expose
    private String accountEmail;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

}
