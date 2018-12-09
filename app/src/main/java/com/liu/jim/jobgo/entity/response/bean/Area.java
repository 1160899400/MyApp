package com.liu.jim.jobgo.entity.response.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jim on 2018/4/23.
 */

public class Area {
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("hostpot")
    @Expose
    private String hostpot;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getHostpot() {
        return hostpot;
    }

    public void setHostpot(String hostpot) {
        this.hostpot = hostpot;
    }
}
