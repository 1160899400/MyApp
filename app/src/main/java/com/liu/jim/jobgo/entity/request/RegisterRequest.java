package com.liu.jim.jobgo.entity.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jim on 2018/4/12.
 */

public class RegisterRequest {
    @SerializedName("accountPhone")
    @Expose
    private String accountPhone;
    @SerializedName("accountPasswd")
    @Expose
    private String accountPassword;
    @SerializedName("smsId")
    @Expose
    private int smsId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("version")
    @Expose
    private int version;

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public int getSmsId() {
        return smsId;
    }

    public void setSmsId(int smsId) {
        this.smsId = smsId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
