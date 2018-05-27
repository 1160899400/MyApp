package com.example.lenovo.myapp.entity.response.result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 用于描述每次请求的相应结果的实体类
 *
 * code取值为0或1：
 *
 * 登录:
 * 1为成功，0为失败
 *
 * 获取验证码：
 * 0为已注册，1为未注册
 * 0为发送失败，1为发送成功
 *
 * 未完待续
 *
 */

public class Result {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("code")
    @Expose
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
