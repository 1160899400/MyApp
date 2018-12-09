package com.liu.jim.jobgo.entity.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jim on 2018/4/21.
 */

public class JobSignedData {
    @SerializedName("data")
    @Expose
    private JobSignedRows data;

    public JobSignedRows getData() {
        return data;
    }

    public void setData(JobSignedRows data) {
        this.data = data;
    }
}
