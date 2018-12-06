package com.liu.jim.jobgo.entity.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 用于表示请求验证码后返回的data字段
 * smsId : 下一步请求时添加的参数，用以标识唯一的验证码请求
 */

public class MessageData {
    @SerializedName("data")
    @Expose
    private int smsId;

    public int getSmsId() {
        return smsId;
    }

    public void setSmsId(int smsId) {
        this.smsId = smsId;
    }
}
