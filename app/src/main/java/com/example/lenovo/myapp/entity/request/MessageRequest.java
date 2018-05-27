package com.example.lenovo.myapp.entity.request;

/**
 * 请求验证码时发送的request实体
 * accountPhone: 登录时的用户名，即为电话
 * type: 注册为1，登录为0
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageRequest {

    @SerializedName("accountPhone")
    @Expose
    private String accountPhone;
    @SerializedName("type")
    @Expose
    private Byte type;

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

}