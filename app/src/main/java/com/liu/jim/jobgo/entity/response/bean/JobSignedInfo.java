package com.liu.jim.jobgo.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lenovo on 2018/4/21.
 */

public class JobSignedInfo {
    @SerializedName("jobBasicInfo")
    @Expose
    private JobBasicInfo jobBasicInfo;
    @SerializedName("appstate")
    @Expose
    private int appstate;

    public JobBasicInfo getJobBasicInfo() {
        return jobBasicInfo;
    }

    public void setJobBasicInfo(JobBasicInfo jobBasicInfo) {
        this.jobBasicInfo = jobBasicInfo;
    }

    public int getAppstate() {
        return appstate;
    }

    public void setAppstate(int appstate) {
        this.appstate = appstate;
    }
}
