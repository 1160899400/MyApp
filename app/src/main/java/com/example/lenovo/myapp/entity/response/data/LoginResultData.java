package com.example.lenovo.myapp.entity.response.data;

import com.example.lenovo.myapp.entity.response.bean.LoginPersonalInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 用于表示login请求结果中data字段
 * data:用户个人信息数据entity
 * token：唯一标识用户登录的字符串
 */

public class LoginResultData {
    @SerializedName("data")
    @Expose
    private LoginPersonalInfo data;


    public LoginPersonalInfo getData() {
        return data;
    }

    public void setData(LoginPersonalInfo data) {
        this.data = data;
    }


}
