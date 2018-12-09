package com.liu.jim.jobgo.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jim on 2018/4/23.
 */

public class Screen {
    @SerializedName("payType")
    @Expose
    private String payType;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("needStudent")
    @Expose
    private String needStudent;
    @SerializedName("urgent")
    @Expose
    private String urgent;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNeedStudent() {
        return needStudent;
    }

    public void setNeedStudent(String needStudent) {
        this.needStudent = needStudent;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
}
