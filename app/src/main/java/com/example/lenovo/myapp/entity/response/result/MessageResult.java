package com.example.lenovo.myapp.entity.response.result;

import com.example.lenovo.myapp.entity.response.data.MessageData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 验证码请求发送后返回的response body 实体
 */

public class MessageResult {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("data")
    @Expose
    private MessageData data;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }
}
